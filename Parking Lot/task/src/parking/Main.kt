package parking

var parkingMap = mutableListOf<Car?>()
var createStatus = false

fun main() {
    while (true) {
        val userInput = readLine().toString()
        if (userInput == "exit") {
            break
        } else startParkingCar(userInput)
    }
}

fun startParkingCar(userInput: String) {
    val userComand = userInput.substringBefore(" ")
    when (userComand) {
        "park" -> parkCar(userInput)
        "leave" -> leaveCar(userInput)
        "create" -> createParking(userInput)
        "status" -> showStatus()
        "reg_by_color" -> if (checkCreating()) regByColor(userInput)
        "spot_by_color" -> if (checkCreating()) spotByColor(userInput)
        "spot_by_reg" -> if (checkCreating()) spotByReg(userInput)
    }
}

fun checkCreating(): Boolean {
    return if (parkingMap.isNullOrEmpty()) {
        println("Sorry, a parking lot has not been created.")
        false
    } else true
}

fun spotByReg(userInput: String) {
    var answer = ""
    val regNumber = userInput.substringAfter(" ")
    for (i in 0 until parkingMap.size)
        if (parkingMap[i] != null && parkingMap[i]!!.ascendingOrder.equals(regNumber, true)) {
            answer = (i + 1).toString()
        }
    if (answer == "") println("No cars with registration number $regNumber were found.")
    else println(answer)
}

fun spotByColor(userInput: String) {
    var answerArray = mutableListOf<String>()
    val color = userInput.substringAfter(" ")
    for (i in 0 until parkingMap.size) {
        if (parkingMap[i] != null && parkingMap[i]!!.color.equals(color, true)) {
            answerArray.add((i + 1).toString())
        }
    }
    if (answerArray.isNullOrEmpty()) println("No cars with color $color were found.")
    else println(answerArray.joinToString(", "))
}

fun regByColor(userInput: String) {
    var answerArray = mutableListOf<String>()
    val color = userInput.substringAfter(" ")
    parkingMap.forEach {
        if (it != null && it.color.equals(color, true)) answerArray.add(it.ascendingOrder)
    }
    if (answerArray.isNullOrEmpty())println("No cars with color $color were found.")
    else println(answerArray.joinToString(", "))
}

fun showStatus() {
    var showStatus = false
    if (createStatus) {
        for (i in 0 until parkingMap.size)
            if (parkingMap[i] != null) {
                println("${i + 1} ${parkingMap[i]!!.ascendingOrder} ${parkingMap[i]!!.color}")
                showStatus = true
            }
        if (!showStatus) println("Parking lot is empty.")
    } else println("Sorry, a parking lot has not been created.")
}

fun createParking(userInput: String) {
    parkingMap.clear()
    val sizeParking = userInput.substringAfter(" ").toInt()
    for (i in 0 until sizeParking) {
        parkingMap.add(null)
    }
    createStatus = true
    println("Created a parking lot with $sizeParking spots.")
}

fun leaveCar(userInput: String) {
    if (createStatus) {
        val parkingPlace = userInput.substringAfter(" ").toInt()
        parkingMap[parkingPlace - 1] = null
        println("Spot $parkingPlace is free.")
    } else println("Sorry, a parking lot has not been created.")
}

fun parkCar(userInput: String) {
    val colorCar = userInput.substringAfterLast(" ")
    val ascendingOredr = userInput.substringAfter(" ").substringBeforeLast(" ")
    var freePlace = 0
    if (createStatus) {
        for (i in 0..parkingMap.size - 1) {
            if (parkingMap[i] == null) {
                freePlace = i + 1
                parkingMap[i] = Car(colorCar, ascendingOredr)
                break
            }
        }
        if (freePlace == 0) println("Sorry, the parking lot is full.")
        else println("$colorCar car parked in spot $freePlace.")
    } else println("Sorry, a parking lot has not been created.")
}


