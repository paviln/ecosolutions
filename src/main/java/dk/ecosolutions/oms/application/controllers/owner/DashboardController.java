package dk.ecosolutions.oms.application.controllers.owner;

import dk.ecosolutions.oms.service.OrderService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Shows order statistics.
 *
 * @author Jens Christensen
 * @version 1.0
 * @since 1.0
 */

public class DashboardController {
    private IntegerProperty weekNumber = new SimpleIntegerProperty(0);
    private int currentWeek;
    @FXML
    private BarChart<String, Number> statistics;
    @FXML
    private TextField week;

    /**
     * Initialized after fxml is loaded.
     */
    @FXML
    public void initialize() {
        Calendar c = Calendar.getInstance();
        currentWeek = c.get(Calendar.WEEK_OF_YEAR);
    }

    @FXML
    public void show() {
        boolean isValid = true;
        try {
            weekNumber.setValue(Integer.parseInt(week.getText()));
        } catch (NumberFormatException e) {
            isValid = false;
        }
        if (isValid) {
            populateChartData();
        }
    }

    @FXML
    public void showThisWeek() {
        weekNumber.setValue(currentWeek);
        week.setText(String.valueOf(currentWeek));
        populateChartData();
    }

    @FXML
    public void showPreviousWeek() {
        if (weekNumber.getValue() > 1) {
            weekNumber.setValue(weekNumber.getValue() - 1);
            week.setText(String.valueOf(weekNumber.getValue()));
            populateChartData();
        }
    }

    private void populateChartData() {
        HashMap<String, Integer> orders = OrderService.getWeekOrders(weekNumber.get());
        statistics.getData().clear();
        XYChart.Series monday = new XYChart.Series();
        monday.setName("Monday");
        monday.getData().add(new XYChart.Data<String, Number>("Monday", orders.get("MONDAY")));
        XYChart.Series tuesday = new XYChart.Series();
        tuesday.setName("Tuesday");
        tuesday.getData().add(new XYChart.Data<String, Number>("Tuesday", orders.get("TUESDAY")));
        XYChart.Series wednesday = new XYChart.Series();
        wednesday.setName("Wednesday");
        wednesday.getData().add(new XYChart.Data<String, Number>("Wednesday", orders.get("WEDNESDAY")));
        XYChart.Series thursday = new XYChart.Series();
        tuesday.setName("Thursday");
        thursday.getData().add(new XYChart.Data<String, Number>("Thursday", orders.get("THURSDAY")));
        XYChart.Series friday = new XYChart.Series();
        friday.setName("Friday");
        friday.getData().add(new XYChart.Data<String, Number>("Friday", orders.get("FRIDAY")));
        XYChart.Series saturday = new XYChart.Series();
        saturday.setName("Saturday");
        saturday.getData().add(new XYChart.Data<String, Number>("Saturday", orders.get("SATURDAY")));
        XYChart.Series sunday = new XYChart.Series();
        sunday.setName("Sunday");
        sunday.getData().add(new XYChart.Data<String, Number>("Sunday", orders.get("SATURDAY")));

        statistics.getData().addAll(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
    }
}
