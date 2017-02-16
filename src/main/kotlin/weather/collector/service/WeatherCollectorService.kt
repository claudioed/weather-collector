package weather.collector.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

/**
 * @author Claudio E. de Oliveira
 * @date 07/02/17.
 * @email claudioed.oliveira@gmail.com
 */
@Service
class WeatherCollectorService(val sender: RabbitSender) {

    val mapper: ObjectMapper = ObjectMapper().registerKotlinModule().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    val interestedCities = listOf(Pair("Sao Paulo", "3448439"), Pair("Americana", "3472343"), Pair("Maragogi", "3395458"), Pair("Santa Barbara dOeste", "3450404"))

    val WEATHER_API_KEY = System.getenv()["WEATHER_API_KEY"]

    @Scheduled(fixedRate = 5000)
    fun collect(): Unit {

        interestedCities.forEach { (getData(it)) }
    }

    private fun getData(params: Pair<String, String>): Unit {
        "http://api.openweathermap.org/data/2.5/weather?id=${params.second}&appid=$WEATHER_API_KEY".httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    throw RuntimeException("Error on collect data")
                }
                is Result.Success -> {
                    val data = mapper.readValue(result.get(), Data::class.java)
                    sender.send(data)
                }
            }
        }

    }

}

data class Data(val main: Temperature, val id: String, val name: String, val wind: Wind, val clouds: Cloud)

data class Temperature(val temp: String, val pressure: String, val humidity: String, val temp_min: String, val temp_max: String)

data class Wind(val speed: String, val deg: String)

data class Cloud(val all: String)