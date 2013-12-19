package test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import service.TbsLoginLogService;
import service.TbsMenuService;
import util.core.MethodUtil;
import model.TbsLoginLogModel;
import model.TbsMenuModel;

@RunWith(SpringJUnit4ClassRunner.class)
    
@ContextConfiguration(locations={"classpath:spring-*.xml"/*,"classpath:spring-mybatis.xml"*/})
public class JunitTest {
	MethodUtil util=new MethodUtil();
	Map<String,Object> map=new HashMap<String, Object>();
	@Autowired
	TbsLoginLogService<TbsLoginLogModel> tbsLoginLogService;
	TbsLoginLogModel tbsLoginLogModel=new TbsLoginLogModel();
	//@Test
    public void tbsLoginLog() throws Exception{
		for(int i=0;i<1000;i++){
			tbsLoginLogModel.setId(util.getUid());
			tbsLoginLogModel.setUsername(util.getUid());
			tbsLoginLogModel.setIp(util.getRandom(0, 255)+"");
			Calendar calendar=Calendar.getInstance();
			//calendar.set(year, month, date, hourOfDay, minute, second)
			int year=util.getRandom(2012, 2019);
			int month=util.getRandom(1, 13)-1;
			int date=util.getRandom(1, 31);
			int hourOfDay=util.getRandom(1, 24);
			int minute=util.getRandom(1, 59);
			int second=util.getRandom(1, 59);
			calendar.set(year, month-1, date, hourOfDay, minute, second);
			calendar.set(2013/*year*/, 7/*month*/-1, 2/*date*/, 17/*hourOfDay*/, minute, second);
			Timestamp timestamp=new Timestamp(calendar.getTimeInMillis());
			tbsLoginLogModel.setCreateTime(timestamp);
			String[] browser=new String[]{"MSIE6","MSIE7","MSIE8","MSIE9","Firefox","Chrome","Safari","Opera"};
			tbsLoginLogService.insert(tbsLoginLogModel);
			//Thread.sleep(1000);
		}
    }
	
	//@Test
	public void chartsTest(){
		try {
			map.put("startTimeFormat", "%Y-%m-%d %H:00");
			map.put("endTimeFormat", "%Y-%m-%d %H:59");
			map.put("groupTimeFormat", "%Y-%m-%d %H:%i");
			List<Map<?, ?>> list=tbsLoginLogService.charts(map);
			System.out.println("list:"+list.size());
			for(int i=0;i<list.size();i++){
				Map<?,?> map=list.get(i);
				int count=((Long) map.get("COUNT(*)")).intValue();
				String time=map.get("createTime").toString();
				System.out.println("count:"+count+"|time:"+time);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	public void tbsLoginLogUpdates(){
		TbsLoginLogModel b=new TbsLoginLogModel();
		b.setId("1306241313291464857");
		TbsLoginLogModel b1=new TbsLoginLogModel();
		b1.setId("1306241313292928726");
		List<TbsLoginLogModel> ts=new ArrayList<TbsLoginLogModel>();
		ts.add(b);
		ts.add(b1);
		/*try {
			tbsLoginLogService.updatesTest(ts);
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
	
	@Autowired
	TbsMenuService<TbsMenuModel> tbsMenuService;
	@Test
	public void like(){
		TbsMenuModel model=new TbsMenuModel();
		String name="导";
		//model.setName("'%"+name+"%'");
		model.setName(name);
		try {
			List<TbsMenuModel> models=tbsMenuService.selectByModel(model);
			System.out.println(JSON.toJSONString(models));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
