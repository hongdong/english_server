package util.json;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonHelper {
	public static final <T> List<T> getList(String jsontext,String list_str,Class<T> clazz){
		JSONObject jsonobj=JSON.parseObject(jsontext);
		if(jsonobj==null){
			return null;
		}
		
		Object obj=jsonobj.get(list_str);
		if(obj==null){
			return null;
		}
		
//		if(obj instanceof JSONObject){}
		if(obj instanceof JSONArray){
			JSONArray jsonarr=(JSONArray)obj;
			List<T> list=new ArrayList<T>();
			for(int i=0;i<jsonarr.size();i++){
				list.add(jsonarr.getObject(i, clazz));
			}
			return list;
		}
		
		return null;
	}
	
	/**
	 * @param <T> -> DepartmentBean
	 * @param jsontext -> {"department":{"id":"1","name":"生产部"},"password":"admin","username":"admin"}
	 * @param obj_str -> department
	 * @param clazz -> DepartmentBean
	 * @return -> T
	 */
	public static final <T> T getObject(String jsontext,String obj_str,Class<T> clazz){
		JSONObject jsonobj=JSON.parseObject(jsontext);
		if(jsonobj==null){
			return null;
		}
		
		Object obj=jsonobj.get(obj_str);
		if(obj==null){
			return null;
		}
		
		if(obj instanceof JSONObject){
			return jsonobj.getObject(obj_str,clazz);
		}else{
			System.out.println(obj.getClass());
		}
		
		return null;
	}
	
	/**
	 * @param <T>
	 * @param jsontext ->{"department":{"id":"1","name":"生产部"},"password":"admin","username":"admin"}
	 * @param clazz -> UserBean.class
	 * @return -> UserBean
	 */
	//注：传入任意的jsontext,返回的T都不会为null,只是T的属性为null
	public static final <T> T getObject(String jsontext,Class<T> clazz){
		return JSON.parseObject(jsontext,clazz);
	}
	
	public static final String toJSONString(Object object,boolean prettyFormat){
		return JSON.toJSONString(object, prettyFormat);
	}
	
}


