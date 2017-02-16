package weather.collector.infra

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * Created by claudio on 15/02/17.
 */
@Configuration
open class RabbitMQ {

    @Autowired
    private var connectionFactory: ConnectionFactory? = null

    @Bean
    open fun amqpAdmin(): AmqpAdmin {
        return RabbitAdmin(this.connectionFactory!!)
    }

    @Bean
    open fun jsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    open fun exchange(): DirectExchange {
        return DirectExchange("weather")
    }

    @Bean
    open fun queue(): Queue {
        return Queue("weather", false)
    }

    @Bean
    open fun rabbitTemplate(): RabbitTemplate {
        val template = RabbitTemplate(this.connectionFactory)
        template.messageConverter = jsonMessageConverter()
        return template
    }

    @Bean
    open fun binding(queue: Queue, exchange: DirectExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange).with("weather")
    }

}