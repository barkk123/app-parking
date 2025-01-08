package com.koma.appparking.api;

import java.math.BigDecimal;

public record ParkingTicketSummary(BigDecimal totalFee, String totalTime) {

}
