package es.jac.gymlog.managers

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class TimerRepository {

    suspend fun getCount(max: Int) = flow {
        var count = max
        while (count > 0){
            delay(1000)
            count--
            emit(count)
        }
    }

}