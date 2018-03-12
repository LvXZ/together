package Match;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Lv_Match implements ActionListener{

	JFrame main_Frame;                              //总容器界面
	
	JMenuBar menuBar;                               //菜单容器
	
	JMenu fileMenu;                                 //菜单
	JMenuItem JuniorItem;                           //菜单栏
	JMenuItem HighItem;
	JMenuItem HeroesItem;                           //英雄榜                 
	JMenuItem HelpItem;                             //帮助
	JMenu SetItem;                                  //设置
	JMenuItem Single;                               //单人
	JMenuItem Double;                               //双人
	
	
	JPanel JP_firstCard = new JPanel();             //首块按钮面板
	JPanel JP_secondCard = new JPanel();            //首块火柴数量面板
	JPanel JP_thirdCard = new JPanel();             //首块游戏面板
	JPanel JP_forthCard = new JPanel();             //首块按钮面板
	JPanel JP_fifthCard = new JPanel();             //首块上轮拿取面板
	
	JButton  JB_return = new JButton("悔步");        
	JButton  JB_againGame = new JButton("重新开始"); 
	JTextField Text_Timer = new JTextField("00:00");//时间显示文本 

	
	JTextField JT_Model;                                //游戏模式文本域
	JLabel JL_remainMatch;                          //剩余火柴文本域
	JLabel JL_Computer;                             //电脑拿取
	JLabel JL_Player;                               //玩家拿取
	JLabel JL_Last_Computer;                        //电脑上轮拿取
	JLabel JL_Last_Player;                          //玩家上轮拿取
	JTextField JT_ComputerTake;                     //电脑输入文本框
	JTextField JT_PlayerTake;                       //人输入文本框
	
	JButton Button1 = new JButton("1");       
	JButton Button2 = new JButton("2");  
	JButton Button3 = new JButton("3");  
	JButton Button_Back = new JButton("退格");  
	JButton Button_Sure = new JButton("确定");  
	
	public static int Stack_Computer[]=new int[100];//电脑拿取栈
	public static int Stack_Player[] = new int[100];//玩家拿取栈
	public static int flag_Com = 0;                 //STACK栈的数字
	public static int flag_Pla = 0;                    
	public static int whoTurn = 2;                  //轮谁拿--2人--1计算机
	public static int remainCount;                  //剩余火柴数量
	
	public static boolean P_C_Hard = true;          //人机模式，初始--困难
	public static boolean Pl_Pl = false;            //人人模式，初始--人机
	
	/**************timer**************/
	Timer time = new Timer(1000,this);
	Date now_date =new Date();
	
	JFrame Heroes_Frame;            //英雄榜界面
	JPanel Heroes_Panel;
	TextArea Heroes_Area;

	JFrame Help_Frame;              //帮助界面
	JPanel Help_Panel;
	TextArea Help_Area;


	@SuppressWarnings("deprecation")
	public Lv_Match()  {
		/*************************顶层容器设置************************/
		main_Frame = new JFrame("LvXZ_Match");
		main_Frame.setBounds(100,50,500,500);
		main_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_Frame.setVisible(true);
		main_Frame.setResizable(false);
		main_Frame.setLayout(new GridLayout(5,1,4,4));//总体面板布局
		main_Frame.add(JP_firstCard);
		main_Frame.add(JP_secondCard);
		main_Frame.add(JP_fifthCard);
		main_Frame.add(JP_thirdCard);
		main_Frame.add(JP_forthCard);
		
		/*************************菜单容器****************************/
		menuBar = new JMenuBar();
		main_Frame.setJMenuBar(menuBar);                  //添加菜单至顶层容器
		fileMenu = new JMenu("游戏");
		HeroesItem= new JMenuItem("英雄榜");

		JuniorItem = new JMenuItem("简单");          //写明列表
		HighItem = new JMenuItem("困难");
		HelpItem = new JMenuItem("帮助");                       //帮助
		SetItem = new JMenu("设置");                          //设置
		Single = new JMenuItem("单人");
		Double = new JMenuItem("双人");
		
		menuBar.add(fileMenu);                         //添加列表至菜单
		fileMenu.add(JuniorItem);
		fileMenu.add(HighItem);
		menuBar.add(SetItem);
		SetItem.add(Single);
		SetItem.add(Double);
		menuBar.add(HeroesItem);
		menuBar.add(HelpItem);
		
		JuniorItem.addActionListener(this);           //监听
		HighItem.addActionListener(this);
		Single.addActionListener(this); 
		Double.addActionListener(this); 
		HeroesItem.addActionListener(this); 
		HelpItem.addActionListener(this);  
		
		/*************************面板容器设置************************/
		JP_firstCard.setLayout(new GridLayout(1,3,4,4));                      //分配1行3列
		JP_firstCard.setBorder(new EmptyBorder(4,4,4,4)); 
		JP_firstCard.add(JB_return);      JB_return.addActionListener(this);  //按钮添加，监听
		JP_firstCard.add(JB_againGame);JB_againGame.addActionListener(this);  
		JP_firstCard.add(new JLabel());
		JP_firstCard.add(Text_Timer);
		Text_Timer.setFocusable(false);                                       //取消光标
		JB_return.setBackground(Color.YELLOW);
		JB_againGame.setBackground(Color.RED);
		
		JP_secondCard.setLayout(new GridLayout(1,3,4,4));                      //分配1行3列
		JP_secondCard.setBorder(new EmptyBorder(4,4,4,4)); 
		JT_Model = new JTextField("人机困难模式");                              //初始--人机困难模式
		JT_Model.setEditable(false);
		JL_remainMatch = new JLabel();
		remainCount = 20+(int)(Math.random()*30);                             //随机生成20~50
		JL_remainMatch.setText("总火柴数量:"+remainCount);
		JL_remainMatch.setBorder(BorderFactory.createLineBorder(Color.BLACK));//创建边框黑色
		JP_secondCard.add(JT_Model);                        
		JP_secondCard.add(JL_remainMatch);               
		JP_secondCard.add(new JLabel());
		

		JP_thirdCard.setLayout(new GridLayout(1,5,2,2));                      //分配1行5列
		JP_thirdCard.setBorder(new EmptyBorder(8,8,8,8)); 
		JL_Computer = new JLabel("电脑拿取");
		JL_Player  = new JLabel("玩家拿取");
		JT_PlayerTake = new JTextField("未开始");
		JT_ComputerTake = new JTextField("未开始");
		JP_thirdCard.add(JL_Computer);
		JP_thirdCard.add(JT_ComputerTake);
		JT_ComputerTake.setFocusable(false); 
		JP_thirdCard.add(new JLabel());
		JP_thirdCard.add(JL_Player);
		JP_thirdCard.add(JT_PlayerTake);
		JT_PlayerTake.setFocusable(false);                        //取消光标
		
		JP_forthCard.setLayout(new GridLayout(1,5,4,4));          //分配1行5列
		JP_forthCard.setBorder(new EmptyBorder(4,4,4,4));         //边框设置
		JP_forthCard.add(Button1);Button1.addActionListener(this);//按钮添加，监听
		JP_forthCard.add(Button2);Button2.addActionListener(this);
		JP_forthCard.add(Button3);Button3.addActionListener(this);
		JP_forthCard.add(Button_Back);Button_Back.addActionListener(this);
		JP_forthCard.add(Button_Sure);Button_Sure.addActionListener(this);
		Button_Back.setBackground(Color.LIGHT_GRAY);
		Button_Sure.setBackground(Color.LIGHT_GRAY);
	
		JP_fifthCard.setLayout(new GridLayout(1,5,4,4));
		JP_fifthCard.setBorder(new EmptyBorder(8,8,8,8));         //边框设置
		
		JP_fifthCard.add(new JLabel("上轮拿取"));
		JL_Last_Computer = new JLabel("未开始");
		JP_fifthCard.add(JL_Last_Computer);
		JP_fifthCard.add(new JLabel());
		JP_fifthCard.add(new JLabel("上轮拿取"));
		JL_Last_Player = new JLabel("未开始");
		JP_fifthCard.add(JL_Last_Player);
		
		main_Frame.pack();
		
		now_date.setMinutes(0);                                  //获取00:00 
		now_date.setSeconds(0);  
		
		Stack_Computer[0] = 0;                                   //栈的初始化
		Stack_Player[0] = 0;
		
		/**************************英雄榜********************************/
		Heroes_Frame = new JFrame("LvXZ_Match--英雄榜");
		Heroes_Frame.setBounds(250,250,350,300);
		Heroes_Frame.setLayout(new BorderLayout());
		Heroes_Frame.setResizable(false);
		
		Heroes_Panel = new JPanel();
		Heroes_Panel.setLayout(new GridLayout(1,1)); 
		Heroes_Panel.add(new Label("  姓名            级别                      用时                   日期"));
		Heroes_Frame.add(BorderLayout.NORTH ,Heroes_Panel);                
		Heroes_Area = new TextArea();
		Heroes_Area.setEditable(false);  //禁止编辑
		Heroes_Area.setFocusable(false); //禁止光标
		Heroes_Frame.add(Heroes_Area);
		
		/**************************帮助********************************/
		Help_Frame = new JFrame("LvXZ_Match--帮助");
		Help_Frame.setBounds(350,250,350,300);
		Help_Frame.setLayout(new BorderLayout());
		Help_Frame.setResizable(false);
		Help_Panel = new JPanel();
		Help_Panel.setLayout(new GridLayout(1,1)); 
		Help_Frame.add(BorderLayout.NORTH ,Help_Panel);                
		Help_Area = new TextArea();
		Help_Area.setEditable(false);  //禁止编辑
		Help_Area.setFocusable(false); //禁止光标
		Help_Frame.add(Help_Area);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == time){
			SimpleDateFormat fm = new SimpleDateFormat("mm:ss");
			Date date = new Date(now_date.getTime()+1000);//只获取秒，赋予now_date
			now_date = date;
			Text_Timer.setText(fm.format(now_date));
		}else if(e.getActionCommand()=="1" || e.getActionCommand()=="2" || e.getActionCommand()=="3"){         
			      time.start();//只要点击面板按钮，就开始计时
		}
		if(e.getActionCommand() == "1"){
			Input("1");	
		}
		else if(e.getActionCommand() == "2"){
			Input("2");
		}
		else if(e.getActionCommand() == "3"){
			Input("3");
		}
		else if(e.getSource() == Button_Back){
			Input("");
		}
		else if(e.getSource() == Button_Sure){
	        if(Pl_Pl == true)		
	            if(whoTurn == 2)    manTake_2();           //玩家2拿火柴处理函数
	            else    manTake_1();                       //玩家1拿火柴处理函数
	        else
	        	manTake_2();                               //人机	
		}
		else if(e.getSource() == JB_return){                //悔步函数
			if(flag_Com>0 && flag_Pla>0) {   
				return_ComputerTake();
				return_PlayerTake();
			}
			else   JOptionPane.showMessageDialog(null, "已经悔步到开始了...");
		}
		else if(e.getSource() == JB_againGame){             //重新开始函数
			AgainGame();  
		}
		else if(e.getSource() == JuniorItem){               //模式的切换必须AgainGame()
			P_C_Hard = false;
			JT_Model.setText("人机简单模式");  
			AgainGame();
		}
		else if(e.getSource() == HighItem){ 
			P_C_Hard = true;
			JT_Model.setText("人机困难模式"); 
			AgainGame();
		}
		else if(e.getSource() == HeroesItem){  //英雄榜
			try {
				Read_Record();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Heroes_Frame.setVisible(true);
		}
		else if(e.getSource() == HelpItem){  //帮助
			Help_Area.setText("游戏说明：随机产生20~50根火柴，双方轮流拿取，每次拿取的数量不超过3根，拿到最后一根的获胜。");
			Help_Frame.setVisible(true);
		}
		else if(e.getSource() == Single){  //单人--恢复简单困难选择
			Pl_Pl = false;
			JB_return.setEnabled(true); 
			whoTurn = 2;
			JuniorItem.setEnabled(true);
			HighItem.setEnabled(true);
			if(P_C_Hard)
				JT_Model.setText("人机困难模式"); //保留原先的选择
			else
				JT_Model.setText("人机简单模式");  
			AgainGame();
	
		}
		else if(e.getSource() == Double){   //双人--取消简单困难选择
			Pl_Pl = true;
			JB_return.setEnabled(false);    //人人之间，落子无悔
			whoTurn = 1;
			JuniorItem.setEnabled(false);
			HighItem.setEnabled(false);
			JT_Model.setText("人人PK模式");  
			AgainGame();
		}
	
	}
	//输入函数
	public void Input(String s){
		if(Pl_Pl == true){	
			if(whoTurn == 2)
				JT_PlayerTake.setText(s);
			else
				JT_ComputerTake.setText(s);
		}
		else
		    JT_PlayerTake.setText(s);
	}
	
	@SuppressWarnings("deprecation")
	public void AgainGame(){
		remainCount = 20+(int)(Math.random()*30);       //随机生成20~50
		JL_remainMatch.setText("总火柴数量:"+remainCount);
		
		time.stop();                                    //时间停止置为00:00
		now_date.setMinutes(0);                         //获取00:00 
		now_date.setSeconds(0); 
		Text_Timer.setText("00:00");                    //重新初始化时间
		JT_PlayerTake.setText("未开始");
		JT_ComputerTake.setText("未开始");
		JL_Last_Computer.setText("未开始");
		JL_Last_Player.setText("未开始");
		flag_Pla=0;                                     //栈重新初始化
		flag_Com=0;
	}
	
	public void manTake_2(){
		
		if(Pl_Pl == true)
		     JL_Last_Computer.setText(String.valueOf(Stack_Computer[flag_Com]));
		
		JT_ComputerTake.setText("");
		
		int y = Integer.parseInt(JT_PlayerTake.getText()); //将字符型转化为整型
		flag_Pla++;
		Stack_Player[flag_Pla] = y;                        //将整型put栈
		
		if(Pl_Pl == true)
			JL_Last_Player.setText(String.valueOf(Stack_Player[flag_Pla-1]));
		else
		    JL_Last_Player.setText(String.valueOf(Stack_Player[flag_Pla])); 
		
		//JT_PlayerTake只设有1个字符，不需考虑外1~3
		remainCount = remainCount - y;
		JL_remainMatch.setText("剩余火柴数量:"+remainCount);
		
		if(remainCount == 0)   displayResult();
		else{
			whoTurn = 1;
			if( !Pl_Pl )     computerTake();                //人机模式--计算机考虑
		}	
	}
	
	public void manTake_1(){
		
		if(Pl_Pl == true)
		     JL_Last_Player.setText(String.valueOf(Stack_Player[flag_Pla]));
		
		JT_PlayerTake.setText("");
		
		int x = Integer.parseInt(JT_ComputerTake.getText()); //将字符型转化为整型
		flag_Com++;
		Stack_Computer[flag_Com] = x;                        //将整型put栈
		
		if(Pl_Pl == true)
			 JL_Last_Computer.setText(String.valueOf(Stack_Computer[flag_Com-1]));
		else
		    JL_Last_Computer.setText(String.valueOf(Stack_Computer[flag_Com])); 
		
		//JT_PlayerTake只设有1个字符，不需考虑外1~3
		remainCount = remainCount - x;
		JL_remainMatch.setText("剩余火柴数量:"+remainCount);
		
		if(remainCount == 0)   displayResult();
		else   whoTurn = 2;
		
	}
	
	public void computerTake(){
		
		JL_Last_Computer.setText(String.valueOf(Stack_Computer[flag_Com])); 
		JT_PlayerTake.setText("");
		int x;
		
		if(P_C_Hard == true){
			if(remainCount%4 == 0)
				x = 1+(int)(Math.random()*3);   //随机拿取1~3
			else
				x = remainCount%4 ;             //拿取4的余数
		}
		else{
			x = 1+(int)(Math.random()*3);
			if(remainCount > 0 && remainCount <= 8) //简单模式下，稍微添加难度
				if(remainCount%4 == 0)
					x = 1+(int)(Math.random()*3);   //随机拿取1~3
				else
					x = remainCount%4 ;             //拿取4的余数     
		}
		
		flag_Com++;
		Stack_Computer[flag_Com] = x;
		
		JT_ComputerTake.setText(String.valueOf(x));
		remainCount = remainCount - x;
		JL_remainMatch.setText("剩余火柴数量:"+remainCount);
		
		if(remainCount == 0)   displayResult();
		else    whoTurn = 2;
	
	}
	
	public void return_PlayerTake(){
		/*********************************玩家返回***************************/
		remainCount = remainCount + Stack_Player[flag_Pla];        
		flag_Pla--;
		if(flag_Pla == 0){
			JT_PlayerTake.setText("0");   JL_Last_Player.setText("0");  
		}
		else
		    JL_Last_Player.setText(String.valueOf(Stack_Player[flag_Pla])); //显示上次玩家选择
		
		JL_remainMatch.setText("剩余火柴数量:"+remainCount);
	}
	
	public void return_ComputerTake(){
		/*********************************电脑返回***************************/
		remainCount = remainCount + Stack_Computer[flag_Com]; 
		flag_Com--;
		if(flag_Com == 0){
			JT_ComputerTake.setText("0");  JL_Last_Computer.setText("0");
		}
		else{
		   JT_ComputerTake.setText(String.valueOf(Stack_Computer[flag_Com]));
		   JL_Last_Computer.setText(String.valueOf(Stack_Computer[flag_Com-1]));//显示上次电脑选择
		}
	}
	
    public void displayResult(){
		if(whoTurn == 2){
			time.stop(); 
			if(Pl_Pl == true ){
            	JOptionPane.showMessageDialog(null, "Player 2 win!");
            }
			else
			    JOptionPane.showMessageDialog(null, "You win!");
			String STR = JOptionPane.showInputDialog("请输入您的姓名");
			if( STR==null ||  STR.equals(""))
				JOptionPane.showMessageDialog(null, "保存已取消！");
			else{
			    try {
				     Save_Data(STR);
			    } catch (IOException e1) {
				     // TODO Auto-generated catch block
				     e1.printStackTrace();
			    }
			}
		}
		else{
			time.stop(); 
            if(Pl_Pl == true ){
            	JOptionPane.showMessageDialog(null, "Player 1 win!");
            	String STR = JOptionPane.showInputDialog("请输入您的姓名");
    			if( STR==null ||  STR.equals(""))
    				JOptionPane.showMessageDialog(null, "保存已取消！");
    			else{
    			    try {
    				     Save_Data(STR);
    			    } catch (IOException e1) {
    				     // TODO Auto-generated catch block
    				     e1.printStackTrace();
    			    }
    			}
            }
            else
			    JOptionPane.showMessageDialog(null, "Computer win!");
		}
	}

	public void Read_Record() throws IOException{
		File myFile = new File("D:/JAVA/Match_Heroes.txt");// 字符流读取文件数据//便于打包
	
	  if(myFile.exists()){
	
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(myFile));
        StringBuilder result=new StringBuilder();
        String theLine=null;
        while(( theLine=reader.readLine())!=null){
            result.append(theLine+"\n");
        }
        Heroes_Area.setText(result.toString());
	  }
	  else
		  JOptionPane.showMessageDialog(null, "尚未有记录！");
	  
	}
	//保存成功数据函数
	public void Save_Data(String STR) throws IOException{
		//字符串写入文件
		StringBuffer STR_user = new StringBuffer(STR);
		
		if(Pl_Pl == true){
			STR_user.append("   人人PK模式    ");
		}
		else
			if(P_C_Hard == true){
				STR_user.append("   人机困难模式    ");
			}
			else{
				STR_user.append("   人机简单模式    ");
			}
	
		STR_user.append(Text_Timer.getText());//获得成功的最短时间
		STR_user.append("   ");
		Date succeed_date=new Date();         //获得成功日期
		SimpleDateFormat SM=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式规定
		STR_user.append(SM.format(succeed_date));
		STR_user.append("\r\n");
		
        FileWriter writer = new FileWriter("D:/JAVA/Match_Heroes.txt",true);
        try {
            writer.write(STR_user.toString());
            writer.flush();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		Lv_Match LvXZ_M = new Lv_Match();
	}

}
