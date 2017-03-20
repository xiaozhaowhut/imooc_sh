package action;

import org.apache.struts2.interceptor.validation.SkipValidation;

import service.UsersDAO;
import service.impl.UsersDAOImpl;

import com.opensymphony.xwork2.ModelDriven;

import entity.Users;

public class UsersAction extends SuperAction implements ModelDriven<Users> {

	/**
	 * 模型驱动，实现ModelDriven接口
	 */
	private static final long serialVersionUID = 1L;
	
	private Users users=new Users();//使用模型驱动，必须实例化Users对象，可以不用getter/setter封装
	//用户登录动作
	public String login(){
		UsersDAO udao=new UsersDAOImpl();
		if(udao.usersLogin(users)){
			//在session中保存登录成功的用户名
			session.setAttribute("loginUserName", users.getUsername());
			
			System.out.println("登陆成功"+":"+users.getUsername()+":"+users.getPassword());
			return "login_success";
		}else {
			
			return"login_failure";
		}
	}
	//用户注销
	@SkipValidation  //不进行表单验证
	public String logout(){
		if(session.getAttribute("loginUserName")!=null){
			session.removeAttribute("loginUserName");
		}
		
		return "logout_success";
	}
	//重写从父类继承的validate方法
	@Override
	public void validate() {	//此方法会对所有的表单进行验证
		//用户名不能为空
		if("".equals(users.getUsername().trim())){
			this.addFieldError("usernameError", "用户名不能为空！");
			
			//System.out.println("用户名不能为空");
		}
		
		if(users.getPassword().length()<1){
			this.addFieldError("passwordError", "密码长度不少于6位");
			//System.out.println("密码长度不能过短");
		}
		
		System.out.println("当前用户的信息"+users.getUsername()+":"+users.getPassword());
	}
	
	
	public Users getModel() {
		
		return users;
	}
}