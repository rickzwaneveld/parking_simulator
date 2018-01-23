package main;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import controller.*;
import logic.SimulatorLogic;
import view.*;

public class Simulator {
	
	private JFrame screen;
	private AbstractView carParkView;
	private AbstractView managerView;
	private SimulatorLogic simulatorLogic;
	private AbstractController runController;	
	
	public Simulator() {
		simulatorLogic = new SimulatorLogic(3, 6, 30);
		carParkView = new CarParkView(simulatorLogic);
		managerView = new ManagerView(simulatorLogic);
		runController = new RunController(simulatorLogic);
		
		screen = new JFrame("Parking Garage Simulator");
		screen.setSize(1980, 1080);
		screen.setResizable(false);
		screen.setLayout(null);
		screen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		screen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		screen.getContentPane().add(carParkView);
		screen.getContentPane().add(managerView);
		screen.getContentPane().add(runController);
		
		carParkView.setBounds(10, 120, 800, 400);
		carParkView.setBackground(Color.WHITE);
		managerView.setBounds(1000,60, 600, 100);
		runController.setBounds(10, 60, 800, 400);
		
		screen.setVisible(true);
	}
}
