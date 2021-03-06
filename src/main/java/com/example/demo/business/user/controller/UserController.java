package com.example.demo.business.user.controller;

import com.example.demo.base.ResponseVo;
import com.example.demo.business.user.entity.Audit;
import com.example.demo.business.user.entity.User;
import com.example.demo.business.user.mapper.UserMapper;
import com.example.demo.business.user.service.UserService;
import com.example.demo.utils.Constants;
import com.example.demo.utils.Tools;
import com.github.pagehelper.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author joy
 * @version 1.0
 * @date 2020/12/28 13:41
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     *
     * @return
     */
    @GetMapping("/userInfo")
    public ResponseVo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        user.setPassword("");
        return ResponseVo.SUCCESS().setData(user);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ResponseVo register(@RequestBody User user){
        return userService.save(user);
    }

    /**
     * 根据当前角色去获取对应菜单
     *
     * @return
     */
    @GetMapping("/getMenu")
    public ResponseVo getMenuList(HttpServletRequest request, HttpServletResponse response) {
        return userService.getMenuList(request, response);
    }

    /**
     * 用户头像上传
     *
     * @param file
     * @return
     */
    @PostMapping("/image/uploadImage")
    public ResponseVo uploadImage(@RequestParam("file") MultipartFile file,
    @RequestParam("original") String original) {
        return userService.uploadImage(file,original);
    }

    /**
     * 获取图片列表
     * @param page
     * @param size
     * @param original
     * @return
     */
    @GetMapping("/image/list")
    public ResponseVo getImageList(@RequestParam("page") int page,
                                  @RequestParam("size") int size,
                                  @RequestParam(value = "original",required = false) String original) {
        return userService.getImageList(page,size,original);
    }

    /**
     * 人员列表
     *
     * @return
     */
    @GetMapping("/personnelList/{page}/{size}")
    public ResponseVo personnelList(@PathVariable(value = "page", required = false) int page,
                                    @PathVariable(value = "size", required = false) int size,
                                    @RequestParam(value = "status",required = false) Integer status,
                                    @RequestParam(value = "keyWords", required = false) String keyWords) {
        return userService.getList(page, size, status,keyWords);
    }

    @Autowired
    private UserMapper userMapper;

    /**
     * 导出excel
     *
     * @return
     */
    @GetMapping("/export")
    public void test(HttpServletResponse response) {
        User currentUser = Tools.getCurrentUser();
        List<User> all = null;
        if(currentUser.getRoles().getRoleName().equals(Constants.Role.ROLE_ADMIN)){
            all = userMapper.findAll();
        }else {
           all = userMapper.findAllByClubId(currentUser.getClubId());
        }

        Tools.exportForExcel(response,all);
    }

    /**
     * 添加人员
     *
     * @return
     */
    @PostMapping("/addPersonnel")
    public ResponseVo addPersonnel(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * 根据人员id更新人员
     *
     * @param user
     * @return
     */
    @PutMapping("/updatePersonnel")
    public ResponseVo updatePersonnel(@RequestBody User user) {
        return userService.update(user);
    }

    /**
     * 根据用户id删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/deletePersonnel/{userId}")
    public ResponseVo deletePersonnel(@PathVariable("userId") String userId){
        return userService.remove(userId);
    }


    /**
     * 入团申请
     * @param audit
     * @return
     */
    @PostMapping("/apply/join")
    public ResponseVo applyJoin(@RequestBody Audit audit){
        return userService.applyJoin(audit);
    }

    /**
     * 退团申请
     * @param audit
     * @return
     */
    @PostMapping("/apply/quit")
    public ResponseVo applyQuit(@RequestBody Audit audit){
        return userService.applyQuit(audit);
    }


        /**
         * 请假申请
         * @param audit
         * @return
         */
        @PostMapping("/apply/leave")
        public ResponseVo applyLeave(@RequestBody Audit audit){
            return userService.applyLeave(audit);
        }

    /**
     * 活动申请
     * @param activityId
     * @return
     */
    @GetMapping("/apply/activity/{activityId}")
    public ResponseVo applyActivity(@PathVariable(value = "activityId") String activityId){
        return userService.applyActivity(activityId);
    }


    @GetMapping("/apply/list/{page}/{size}")
    public ResponseVo applyList(@PathVariable(value = "page", required = false) int page,
                                @PathVariable(value = "size", required = false) int size){
            return userService.applyList(page,size);
    }


    @PostMapping("/apply/delete/{id}")
    public ResponseVo applyDelete(@PathVariable(value = "id", required = false) String id){
        return userService.applyDelete(id);
    }

    @GetMapping("/system/msg")
    public ResponseVo getActivityInfo(){
        return userService.getActivityInfo();
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 重置密码
     *
     * @param user
     * @return
     */
    @PutMapping("/resetPwd")
    public ResponseVo resetPwd(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        boolean success = userMapper.resetPassWordById(user) == 1;
        if (!success){
            return ResponseVo.FAILURE().setMsg("修改失败");
        }
        return ResponseVo.SUCCESS().setMsg("修改成功");
    }

}
