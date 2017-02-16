package weather.collector.service

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

/**
 * Created by claudio on 15/02/17.
 */
@Service
class RabbitSender(val rabbitTemplate: RabbitTemplate){

    fun send(data: Data):Unit{
        rabbitTemplate.convertAndSend("weather",data)
    }

}