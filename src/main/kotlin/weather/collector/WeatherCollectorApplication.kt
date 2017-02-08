package weather.collector

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @author Claudio E. de Oliveira
 * @date 07/02/17.
 * @email claudioed.oliveira@gmail.com
 */
@SpringBootApplication
@EnableScheduling
open class WeatherCollectorApplication {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(WeatherCollectorApplication::class.java, *args)
        }
    }

}