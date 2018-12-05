package top.cflwork.config;

import top.cflwork.common.quartz.factory.JobFactory;
import org.quartz.Scheduler;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfigration {

	@Resource
	private JobFactory jobFactory;


	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		try {
			schedulerFactoryBean.setOverwriteExistingJobs(true);
			schedulerFactoryBean.setQuartzProperties(quartzProperties());
			schedulerFactoryBean.setJobFactory(jobFactory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return schedulerFactoryBean;
	}

	// 指定quartz.properties
	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/config/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	// 创建schedule
	@Bean(name = "scheduler")
	public Scheduler scheduler() {
		return schedulerFactoryBean().getScheduler();
	}
}
