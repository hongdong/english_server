package util.json;


public class JsonEx{
	
	 
	 public static void main(String[] args) {
	   /* 	System.out.println(System.currentTimeMillis());
			  TbcMenuModel tbcMenuModel=new TbcMenuModel();
			  tbcMenuModel.setButton("bt");
			  tbcMenuModel.setUrl("url");
			  tbcMenuModel.setId(null);
			  tbcMenuModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
			  //属性过滤器
			  PropertyFilter propertyFilter=new PropertyFilter() {
				public boolean apply(Object arg0, String arg1, Object arg2) {
					if("pageUtil".equals(arg1)){
						return false;
					}
					return true;
				}
			  };
			  NameFilter nameFilter=new NameFilter() {
				
				public String process(Object arg0, String arg1, Object arg2) {
					if("url".equals(arg1)){
						return "reurl";
					}
					return arg1;
				}
			};
			ValueFilter valueFilter=new ValueFilter() {
				
				public Object process(Object arg0, String arg1, Object arg2) {
					//System.out.println(arg1+"|"+arg2);
					if(arg2==null){
						return "";
					}
					return arg2;
				}
			};
			
			SerializeWriter out = new SerializeWriter();
			JSONSerializer serializer = new JSONSerializer(out);
			serializer.getPropertyFilters().add(propertyFilter);
			serializer.getNameFilters().add(nameFilter);
			serializer.getValueFilters().add(valueFilter);
			serializer.getMapping().put(java.sql.Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
			serializer.config(SerializerFeature.WriteMapNullValue, true);
			serializer.write(tbcMenuModel);
			System.out.println(out.toString());
			
			SerializerFeature[] features = { 
				SerializerFeature.WriteMapNullValue, // 输出空置字段
		        SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
		        SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
		        SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
		        SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
		      };
			System.out.println(JSON.toJSONString(tbcMenuModel, features));
			*/
		}
}
