package com.jarcheng.mbg.common;


/**
 * ========================
 * 通用响应状态
 * <p>
 * ========================
 */
public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(0, "操作成功！"),

    /* 错误状态码 */
    FAIL(-1, "操作失败！"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数格式错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录，请先登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),

    /* 业务错误：30001-39999 */
    BUSINESS_GROUP_NO_ALLOWED_DEL(30001, "应用分组已经被应用使用，不能删除"),
    BUSINESS_THEME_NO_ALLOWED_DEL(30002, "主题已经被用户使用，不能删除"),
    BUSINESS_THEME_NO_ALLOWED_DISABLE(30003, "主题已经被用户使用，不能停用"),
    BUSINESS_THEME_DEFAULT_NO_ALLOWED_DEL(30004, "默认主题，不能删除"),
    BUSINESS_THEME_NO_ALLOWED_UPDATE(30005, "主题已经被用户使用，不能修改图片信息"),
    BUSINESS_IS_TOP(30040, "已经到最顶部"),
    BUSINESS_IS_BOTTOM(30041, "已经到最底部"),
    BUSINESS_NAME_EXISTED(30051, "名称已存在"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),
    UPLOAD_ERROR(40002, "系统异常，上传文件失败"),
    FILE_MAX_SIZE_OVERFLOW(40003, "上传尺寸过大"),
    FILE_ACCEPT_NOT_SUPPORT(40004, "上传文件格式不支持"),
    SET_UP_AT_LEAST_ONE_ADMIN(40005, "至少指定一个管理员"),
    URL_INVALID(40006, "地址不合法"),
    LINK_AND_LOGOUT_NO_MATCH(40006, "主页地址和注销地址IP不一致"),
    IP_AND_PORT_EXISTED(40007, "当前IP和端口已经被占中"),
    LINK_IS_REQUIRED(40008, "生成第三方token认证信息： 主页地址不能为空,请完善信息"),
    ONLY_ROOT_DEPARTMENT(40009, "组织机构只能存在一个根机构"),
    DEPART_CODE_EXISTED(40010, "组织机构编码已存在"),
    DEPART_CONTAINS_USERS(40011, "该机构下是存在用户，不允许删除"),
    DEPART_CONTAINS_SON(40012, "该机构下是存在子级机构，不允许删除"),
    DEPART_PARENT_IS_SELF(40013, "选择的父机构不能为本身"),
    DICT_EXIST_DEPEND(40014, "该字典数据存在详情依赖，不允许删除"),
    DICT_DETAIL_LOCK(40015, "该字典数据被锁定，不允许修改或删除"),
    DEPART_CODE_EXISTED_WITH_ARGS(40016, "组织机构编码【{0}】系统已存在"),

    /* 数据错误：50001-599999 */
    RESULT_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),
    AUTH_CODE_ERROR(50004, "验证码错误"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),

    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),

    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),

    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),

    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),

    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),


    /* 权限错误：70001-79999 */
    PERMISSION_UNAUTHENTICATED(70001, "此操作需要登陆系统！"),

    PERMISSION_UNAUTHORISE(70002, "权限不足，无权操作！"),

    PERMISSION_EXPIRE(70003, "登录状态过期！"),

    PERMISSION_TOKEN_EXPIRED(70004, "token已过期"),

    PERMISSION_LIMIT(70005, "访问次数受限制"),

    PERMISSION_TOKEN_INVALID(70006, "无效token"),

    PERMISSION_SIGNATURE_ERROR(70007, "签名失败"),

    PERMISSION_VIP(70000, "权限不足,此影片需要大会员,请开通大会员");

    //操作代码
    int code;
    //提示信息
    String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
