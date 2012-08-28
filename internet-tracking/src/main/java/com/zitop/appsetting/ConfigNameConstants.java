package com.zitop.appsetting;

/**配置参数名称常量
 * @author william
 *
 */
public class ConfigNameConstants
{
	public final static String CAPTCHA_NAME="__captcha_paptcha";
	
	//---------图片上传配置--------------
    //图片上传后所在目录
	public final static String IMAGE_FILE_FOLD="image_file_fold";
	//上传最大图片大小 M为单位
	public final static String IMAGE_MAX_SIZE="image_max_size";
	//上传后访问图片的路径
	public final static String IMAGE_VIEW_URL="image_view_url";
	//支持的图片格式
	public final static String IMAGE_ALLOWED="image_allowed";
	//----------end 图片上传配置---------
	
	//---------文件上传配置--------------
	//文件上传所在目录
	public final static String UPLOAD_FILE_FOLD="upload_file_fold";
	//上传最大文件大小 M为单位
	public final static String FILE_MAX_SIZE="file_max_size";
	//下载文件路径
	public final static String FILE_DOWNLOAD_URL="file_download_url";
	//支持评测上传文件的格式
	public final static String FILE_ALLOWED_EVALUATE="file_allowed_evaluate";
	//---------end 文件上传配置--------------
	
	//是否将缓存页面上的权限到session中AuthorizeResourceTag
    public final static String CACHED_AUTHORITY_IN_SESSION="cached_authority_in_session";
    
    public final static String CURRENT_SYSTEM_USER_ACCOUNT = "_currentSystemUserAccount";//session 中当前用户帐号
	public final static String CURRENT_SYSTEM_USER = "_currentSystemUser";//session 中当前用户对象
	
	//字典的关注点目录
	public final static String DIRECTORY_CAT_FOCUS = "focus";
	//问题来源类型
	public final static String E_ISSUE_SOURCE="e_issue_source";
	//问题等级类型
	public final static String E_ISSUE_LEVEL="e_issue_level";
	//问题类型
	public final static String E_ISSUE_TYPE="e_issue_type";
	public final static String E_TASK_ISSUE_TYPE="e_task_issue_type";
	//问题属性类型
	public final static String E_ISSUE_PROPERTY="e_issue_property";
	//问题状态类型
	public final static String E_ISSUE_STATUS="e_issue_statue";
	//问题处理与建议类型
	public final static String E_ISSUE_SUGGEST="e_issue_sugget";
   //问题测试机型
   public final static String E_ISSUE_TEST_MODEL="e_issue_test_model";
	//问题测试系统
   public final static String E_ISSUE_TEST_SYSTEM="e_issue_test_system";
	//填空题类型的选项编码
	public final static String OPTIONS_BLANK_FILLING = "blank_filling";
	
	//脚本保存的用例
	public final static String SCRIPT_CASE_TEMP = "script_case_temp";
	
	//系统消息
	public final static String SYSTEM_MESSAGE = "system_message";
	
	//前端用户登录
	public final static String CURRENT_CUSTOMER_NAME = "_currentCustomerName";
	public final static String CURRENT_CUSTOMER_ACCOUNT = "_currentCustomerAccount";
	public final static String CURRENT_CUSTOMER = "_currentCustomer";
	
	//模板
	public final static String EVALUATE_SCRIPT_PATH = "evaluate_script_path";
	public final static String EVALUATE_SCRIPT_XML = "evaluate_script_xml";
	public final static String EVALUATE_SCRIPT_RAR = "evaluate_script_rar";
	
	//#答卷中间保存路径
	public final static String ANSWER_CACHE_PATH = "answer_cache_path";
	
	//有管理员角色的roles
	public final static String  ADMIN_ROLES = "admin_roles";
	
	
	
}
