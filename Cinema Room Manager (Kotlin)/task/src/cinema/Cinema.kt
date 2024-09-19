package cinema

fun main() {


    // Check room size
    val (rows, seats) = getCinemaDimensions()
    val cinema = createCinema(rows, seats)
    val totalSeats = cinema.size * (cinema[0].size - 1)
    println()
    var ticketsBought = 0
    var ticketCounter = 0

    var exit = false
    while (!exit) {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        print("> ")
        val input = readln().toInt()
        println()


        when (input) {
            1 -> // display cinema
                displayCinema(cinema, seats)

            2 -> {  // select and book seat
                var ticketSale = true

                do {
                    val (rowNumber, seatNumber) = getSeatSelection()
                    println()
                    if (rowNumber < 0 || rowNumber >= cinema.size || seatNumber < 0 || seatNumber >= cinema[rowNumber].size) {
                        println("Wrong input!")
                        println()
                        ticketSale = false

                    } else if (cinema[rowNumber][seatNumber] == "B") {
                        println("That ticket has already been purchased")
                        println()
                        ticketSale = false
                    } else {

                        bookSeat(cinema, rowNumber, seatNumber)
                        // calculate price
                        val ticketPrice = calculateTicketPrice(rowNumber, rows, totalSeats)
                        println("Ticket price: $$ticketPrice\n")
                        ticketsBought += ticketsBought(ticketPrice)
                        ticketCounter++
                        ticketSale = true
                    }
                } while (!ticketSale)
            }

            3 -> {
                statistics(ticketsBought, ticketCounter, totalSeats)
                val totalIncome = totalIncome(rows, seats)
                println("Total income: $$totalIncome")
            }

            0 -> exit = true

        }
    }

}


// return rows and seats
fun getCinemaDimensions(): Pair<Int, Int> {
    println("Enter the number of rows:")
    print("> ")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    print("> ")
    val seats = readln().toInt()
    return Pair(rows, seats)
}

// create cinema
fun createCinema(rows: Int, seats: Int): MutableList<MutableList<String>> {
    val cinema = MutableList(rows) { MutableList(seats + 1) { "S" } }
    for (i in 1..rows) {
        cinema[i - 1][0] = i.toString()
    }
    return cinema
}

// dispay the cinema
fun displayCinema(cinema: MutableList<MutableList<String>>, seats: Int) {
    println()
    println("Cinema:")
    print("  ")

    // print the seat numbers
    for (i in 1..seats) {
        print("$i ")
    }
    println()
    // print all rows with number and space.
    for (row in cinema) {
        println(row.joinToString(" "))
    }
    println()

}

// choose a seat
fun getSeatSelection(): Pair<Int, Int> {
    println("Enter a row number:")
    print("> ")
    val rowNumber = readln().toInt() - 1
    println("Enter a seat number in that row:")
    print("> ")
    val seatNumber = readln().toInt()
    return Pair(rowNumber, seatNumber)
}

// Book a seat
fun bookSeat(cinema: MutableList<MutableList<String>>, row: Int, seat: Int) {


    cinema[row][seat] = "B"


}

// calculate price
fun calculateTicketPrice(row: Int, totalRows: Int, totalSeats: Int): Int {

    if (totalSeats <= 60) {
        return 10
    }
    val halfRows = totalRows / 2

    return if (row < halfRows) {
        10
    } else {
        8
    }

}

fun totalIncome(rows: Int, seats: Int): Int {
    var income = 0

    for (row in 0 until rows) {
        for (seat in 1..seats) {
            income += calculateTicketPrice(row, rows, rows * seats)
        }
    }
    return income
}

fun ticketsBought(ticketPrice: Int): Int {
    var tick8 = 0
    var tick10 = 0
    if (ticketPrice == 8) {
        tick8++
    } else tick10++

    return (tick10 * 10) + (tick8 * 8)
}

fun statistics(ticketsBought: Int, ticketCounter: Int, totalSeats: Int) {

    val percentage = (ticketCounter.toDouble() / totalSeats.toDouble()) * 100
    val formatPercentage = "%.2f".format(percentage)


    //println(totalSeats)
    println("Number of purchased tickets: $ticketCounter")
    println("Percentage: $formatPercentage%")
    println("Current income: $$ticketsBought")
}
