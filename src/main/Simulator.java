package main;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controller.*;
import model.SimulatorModel;
import view.*;

/**
 * This class is responsible for assembling the application. It creates an
 * instance of the model and adds this, with all the controllers and views to
 * a screen. It also sets all the controllers and views at its designated
 * places. And makes sure everything is made visible. 
 * 
 * @author Rick Zwaneveld
 * @version 23-01-2018
 */

public class Simulator {
	
	private JFrame screen;
	private AbstractView carParkView;
	private AbstractView carQueueView;
	private AbstractView financialView;
	private AbstractView timeView;
	private SimulatorModel simulatorLogic;
	private AbstractController runController;	
	private AbstractController datacontroller;
	private AbstractView BarChart;
	/**
	 * The constructor for this class.
	 */
	public Simulator() {
		simulatorLogic = new SimulatorModel(3, 6, 30);
		carParkView = new CarParkView(simulatorLogic);
		carQueueView = new CarQueueView(simulatorLogic);
		financialView = new FinancialView(simulatorLogic);
		timeView = new TimeView(simulatorLogic);
		runController = new RunController(simulatorLogic);
		datacontroller = new DataController(simulatorLogic);
		BarChart = new BarChartView(simulatorLogic);
		
		
		screen = new JFrame("Parking Garage Simulator");
		screen.setSize(1980, 1080);
		screen.setResizable(false);
		screen.setLayout(null);
		screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		screen.getContentPane().add(carParkView);
		carParkView.setBounds(10, 120, 800, 400);

		screen.getContentPane().add(carQueueView);
		
		screen.getContentPane().add(financialView);
		financialView.setBounds(1000, 60, 600, 100);
		
		screen.getContentPane().add(timeView);
		timeView.setBounds(10, 600, 100, 25);

		screen.getContentPane().add(runController);
		runController.setBounds(10, 60, 300, 25);
		
		screen.getContentPane().add(datacontroller);
		datacontroller.setBounds(-250, 600, 800, 900);

	screen.getContentPane().add(BarChart);
		BarChart.setBounds(1000, 240, 600,600);
		
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setVisible(true);
	}
}
