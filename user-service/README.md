spring:
  redis:
    host: 127.0.0.1
    port: 6379
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    url: 'jdbc:mysql://localhost:3306/user?useSSL=false&useUnicode=true&characterEncoding=utf8'
    username: root
    password: '123456'
    hikari:
      minimum-idle: 5
      maximum-pool-size: 15
      auto-commit: true
      idle-timeout: 30000
      pool-name: DatebookHikariCP
      max-lifetime: 1800000
      connection-timeout: 30000
      connection-test-query: SELECT 1
  jpa:
    show-sql: true
    database: mysql
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
    hibernate:
      ddl-auto: update
  cloud:
    stream:
      binders:
        rabbit:
          type: rabbit
          environment:
            spring:
              rabbitmq:
                host: 127.0.0.1
                port: 5672
                username: admin
                password: 123456
                virtual-host: /
        nats:
          type: nats
          environment:
            nats:
              spring:
                cloud:
                  stream:
                    binder:
                      server: nats://localhost:4222
      bindings:
        user-info-message:
          destination: local.user.message
          content-type: application/json
          binder: rabbit
        user-info-receive:
          destination: local.user.message
          content-type: application/json
          binder: rabbit
        log-info:
          destination: local.log.message
          content-type: application/json
          binder: nats
        log-info-receive:
          destination: local.log.message
          content-type: application/json
          binder: nats
        log-error:
          destination: local.log.error
          content-type: application/json
          binder: nats
        log-error-receive:
          destination: local.log.error
          content-type: application/json
          binder: nats
      default-binder: rabbit
org:
  ilmostro:
    name: "name"
