package top.cflwork.common.task;

import top.cflwork.domain.Response;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WelcomeJob implements Job{
	@Autowired
	SimpMessagingTemplate template;
    public void test(JobExecutionContext arg0) throws JobExecutionException {
    	template.convertAndSend("/topic/getResponse", new Response("欢迎体验bootdo,这是一个任务计划，使用了websocket和quzrtz技术，可以在计划列表中取消，欢迎您加入qq群交流学习!" ));

    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        test(jobExecutionContext);
    }
}