package com.teamteach.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import com.teamteach.gateway.filters.pre.SimpleFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableZuulProxy
@SpringBootApplication
@CrossOrigin("*")
public class TeamTeachAPIGateway {

  public static void main(String[] args) {
    SpringApplication.run(TeamTeachAPIGateway.class, args);
  }

  @Bean
  public SimpleFilter simpleFilter() {
    return new SimpleFilter();
  }

}
