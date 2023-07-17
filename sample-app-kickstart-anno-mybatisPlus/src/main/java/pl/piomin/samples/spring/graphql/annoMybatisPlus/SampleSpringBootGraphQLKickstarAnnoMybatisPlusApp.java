package pl.piomin.samples.spring.graphql.annoMybatisPlus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan
@SpringBootApplication
public class SampleSpringBootGraphQLKickstarAnnoMybatisPlusApp {

	public static void main(String[] args) {
		SpringApplication.run(SampleSpringBootGraphQLKickstarAnnoMybatisPlusApp.class, args);
	}

}
