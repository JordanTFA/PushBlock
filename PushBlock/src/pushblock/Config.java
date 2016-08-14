package pushblock;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Config {
	
	public static String topLeft = "Q", topRight = "I", midLeft = "W", midRight = "O", botLeft = "E", botRight = "P", pause = "Escape", mute = "M";
	private static ArrayList<Choice> choices;
	
	public static void openConfig(JFrame frame){
		
		choices = new ArrayList<Choice>();
		
		JFrame options = new JFrame("Options");
		options.setSize(300, 300);
		options.setResizable(false);
		options.setLocationRelativeTo(frame);
		
		Choice topLeft = addChoice("Top Left", options, 30, 30);
		topLeft.select(Config.topLeft);
		Choice topRight = addChoice("Top Right", options, 150, 30);
		topRight.select(Config.topRight);
		Choice midLeft = addChoice("Middle Left", options, 30, 80);
		midLeft.select(Config.midLeft);
		Choice midRight = addChoice("Middle Right", options, 150, 80);
		midRight.select(Config.midRight);
		Choice botLeft = addChoice("Bottom Left", options, 30, 130);
		botLeft.select(Config.botLeft);
		Choice botRight = addChoice("Bottom Right", options, 150, 130);
		botRight.select(Config.botRight);
		Choice pause = addChoice("Pause", options, 30, 180);
		pause.select(Config.pause);
		Choice mute = addChoice("Mute", options, 150, 180);
		mute.select(Config.mute);
		
		JButton save = new JButton("Save");
		save.setBounds(90, 220, 100, 30);
		save.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				options.dispose();
				saveChanges();	
			}
		});
		
		options.add(save);
		
		options.setLayout(null);
		options.setVisible(true);
	}
	
	public static void saveChanges(){
		
		Choice topLeft = choices.get(0);
		Choice topRight = choices.get(1);
		Choice midLeft = choices.get(2);
		Choice midRight = choices.get(3);
		Choice botLeft = choices.get(4);
		Choice botRight = choices.get(5);
		Choice pause = choices.get(6);
		Choice mute = choices.get(7);
		
		Config.topLeft = topLeft.getSelectedItem();
		Config.topRight = topRight.getSelectedItem();
		Config.midLeft = midLeft.getSelectedItem();
		Config.midRight = midRight.getSelectedItem();
		Config.botLeft = botLeft.getSelectedItem();
		Config.botRight = botRight.getSelectedItem();
		Config.pause = pause.getSelectedItem();
		Config.mute = mute.getSelectedItem();
		
		try {
			saveConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static Choice addChoice(String name, JFrame options, int x, int y) {
		
		JLabel lbl = new JLabel(name);
		lbl.setBounds(x, y-20, 100, 20);
		Choice key = new Choice();
		for(String s: getKeyNames()){
			key.add(s);
		}
		
		key.setBounds(x, y, 100, 20);
		options.add(key);
		options.add(lbl);
		
		return key;
	}
	
	public static ArrayList<String> getKeyNames(){
		
		ArrayList<String> result = new ArrayList<String>();
		
		for(String s: KeyGetter.keyNames){
			result.add(s);
			if(s.equalsIgnoreCase("F24")){
				break;
			}
		}
		
		return result;
	}
	
	public static void loadConfig() throws Exception{
		
		File directory = new File(getDefaultDirectory(), "Push Block");
		if(!directory.exists()){
			directory.mkdirs();
		}
		File config = new File(directory, "/config.text");
		if(!config.exists()){
			config.createNewFile();
			
			System.out.println("File not found - Creating new file");
			
			saveConfig();
			return;
		}
		
		Scanner scan = new Scanner(config);
		HashMap<String, String> values = new HashMap<String, String>();
		
		while(scan.hasNextLine()){
			String[] entry = scan.nextLine().split(":");
			String key = entry[0];
			String value = entry[1];
			values.put(key, value);
		}
		if(values.size() != 8){
			System.out.println("Wrong number of values");
			saveConfig();
			return;
		}
		if(!values.containsKey("Top Left") || !values.containsKey("Top Right") || !values.containsKey("Middle Left") || !values.containsKey("Middle Right") || !values.containsKey("Bottom Left") || !values.containsKey("Bottom Right") || !values.containsKey("Bottom Right") || !values.containsKey("Pause") || !values.containsKey("Mute")){
			System.out.println("Invalid names in config... Saving default");
			saveConfig();
			return;
		}
		
		String tl = values.get("topLeft");
		String tr = values.get("topRight");
		String ml = values.get("midLeft");
		String mr = values.get("midRight");
		String bl = values.get("botLeft");
		String br = values.get("botRight");
		String pause = values.get("pause");
		String mute = values.get("mute");
		
		if(!(getKeyNames().contains(topLeft)) && !(getKeyNames().contains(topRight)) && !(getKeyNames().contains(midLeft)) && !(getKeyNames().contains(midRight)) && !(getKeyNames().contains(botLeft)) && !(getKeyNames().contains(botRight)) && !(getKeyNames().contains(pause)) && !(getKeyNames().contains(mute))){
			
			System.out.println("Invalid key in config... Saving default");
			return;
		}
		
		Config.topLeft = tl;
		Config.topRight = tr;
		Config.midLeft = ml;
		Config.midRight = mr;
		Config.botLeft = bl;
		Config.botRight = br;
		Config.pause = pause;
		Config.mute = mute;
		
	}

	public static void saveConfig() throws Exception{
		
		File directory = new File(getDefaultDirectory(), "Push Block");
		if(!directory.exists()){
			directory.mkdirs();
		}
		File config = new File(directory, "/config.txt");
		
		PrintWriter pw = new PrintWriter(config);
		
		pw.println("Top left:" + topLeft);
		pw.println("Top Right:" + topRight);
		pw.println("Middle Left:" + midLeft);
		pw.println("Middle Right:" + midRight);
		pw.println("Bottom Left:" + botLeft);
		pw.println("Bottom Right:" + botRight);
		pw.println("Pause:" + pause);
		pw.println("Mute:" + mute);
		
		pw.close();
		
	}
	
	public static String getDefaultDirectory() {
		
		String OS = System.getProperty("os.name").toUpperCase();
		if(OS.contains("WIN")){
			return System.getenv("APPDATA");
		}
		else if(OS.contains("MAC")){
			return System.getProperty("user.home") + "Library/Application " + "Support";
		}
		else if(OS.contains("NUX")){
			return System.getProperty("user.home");
		}
		else{
			return System.getProperty("user.home");
		}
	}
}
