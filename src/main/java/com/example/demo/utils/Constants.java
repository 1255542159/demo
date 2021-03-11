package com.example.demo.utils;

/**
 * @author joy
 * @version 1.0
 * @date 2021/2/28 16:48
 */
public interface Constants {
    interface DEFAULT {
        String AVATAR = "https://imgs.sunofbeaches.com/group1/M00/00/07/rBsADV22ZymAV8BwAABVL9XtNSU926.png";
    }
    interface Page {
        int DEFAULT_PAGE = 1;
        int MIN_SIZE = 5;
    }

    /**
     * 角色
     */
    interface Role {
        String ROLE_ADMIN = "ROLE_ADMIN";
        String ROLE_CLUB = "ROLE_CLUB";
        String ROLE_USER = "ROLE_USER";
    }

    /**
     * 活动状态
     */
    interface ActivityStatus {
        int TO_AUDIT = 0; //待审核
        int PASSED = 1;   //审核通过
        int UNAPPROVE = 2;//审核未通过
    }

    /**
     * 图片类型
     */
    interface ImageOriginal {
        String ACTIVITY = "activity";
        String AVATAR = "avatar";
        String CLUB = "club";
    }
}
