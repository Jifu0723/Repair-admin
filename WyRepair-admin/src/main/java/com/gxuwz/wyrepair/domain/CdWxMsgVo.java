package com.gxuwz.wyrepair.domain;


import java.util.Map;

/**
 * @author LMTeam
 */
public class CdWxMsgVo {
    /** 用户openid */
    private String touser;
    /** 模版id */
    private String template_id;
    /** 跳到小程序页面 */
    private String page;
    /** 放大那个推送字段 */
    private final String emphasis_keyword = "keyword1.DATA";
    /** 推送文字 */
    private Map<String, Object> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getEmphasis_keyword() {
        return emphasis_keyword;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}



