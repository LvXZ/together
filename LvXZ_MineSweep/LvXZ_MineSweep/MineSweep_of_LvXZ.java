package LvXZ_MineSweep;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.*;


public class MineSweep_of_LvXZ  extends MinesArea implements ActionListener{
	  
	   public static final int Width[] = {300,680,1350,1300};   //Frame边框--等级初始化
	   public static final int Height[] ={340,670,670,1300};
    
	   public static int Mines_Count = MinesNum[Rank];
	   public static int Surplus_Count = MAX;     //剩余按钮计数

		JFrame mainFrame;                         //顶层容器--总窗口界面
		JMenuBar menuBar;                         //菜单容器
		
		JMenu fileMenu;                           //菜单
		JMenu HeroLevel_Menu;
		
		JMenuItem JuniorItem;                     //菜单栏
		JMenuItem SeniorItem;
		JMenuItem HighItem;
		JMenuItem SuperItem;
		JMenuItem HeroItem;

		JPanel Jp_Button = new JPanel();          //按钮面板
		JPanel Jp_Count = new JPanel();           //计数面板
		
		JTextField Text_Count;                    //地雷剩余数文本
		JTextField Text_Timer;                    //时间显示文本
		
		JButton Jbutton[] = new JButton[2500];    //假设存在最多
		
		MouseListener listen ;
		JButton Restart_button;
		
		/**************timer**************/
		Timer time = new Timer(1000,this);
		Date now_date =new Date();  
		
		JFrame Super_Frame;
		JPanel Super_Panel;
		TextArea Super_Area;
	
		
		MineSweep_of_LvXZ(){
			/*************************顶层容器1设置************************/
			mainFrame = new JFrame("LvXZ_MineSweep--扫雷");
			mainFrame.setBounds(50,0,320,320);
			mainFrame.setLayout(new BorderLayout());
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setVisible(true);
			mainFrame.setResizable(false);
			
			/*************************菜单容器****************************/
			menuBar = new JMenuBar();
			mainFrame.setJMenuBar(menuBar);                  //添加菜单至顶层容器
			
			fileMenu = new JMenu("游戏");
			HeroLevel_Menu = new JMenu("英雄榜");

			JuniorItem = new JMenuItem("初级9x9");          //写明列表
			SeniorItem = new JMenuItem("中级16x16");
			HighItem = new JMenuItem("高级16x30");
			SuperItem = new JMenuItem("精神病级30x30");
			HeroItem = new JMenuItem("英雄名单");
			
			menuBar.add(fileMenu);                         //添加列表至菜单
			fileMenu.add(JuniorItem);
			fileMenu.add(SeniorItem);
			fileMenu.add(HighItem);
			fileMenu.add(SuperItem);
			menuBar.add(HeroLevel_Menu);
			HeroLevel_Menu.add(HeroItem);
			
			JuniorItem.addActionListener(this);           //监听
			SeniorItem.addActionListener(this);
			HighItem.addActionListener(this);
			SuperItem.addActionListener(this);
			HeroItem.addActionListener(this);
			
		    Text_Count = new JTextField(String.valueOf(MinesNum[Rank]));     //地雷初始化
			Text_Timer = new JTextField("00:00");
			Restart_button = new JButton("重新开始");
			
			Jp_Count.setLayout(new GridLayout(1,5));                         //计数面板设置
			Jp_Count.add(Text_Count);
			Jp_Count.add(new Label("  P:插旗"));
			Jp_Count.add(Restart_button);
			Restart_button.addActionListener(this);
			Jp_Count.add(new Label("  B:炸弹"));
			Jp_Count.add(Text_Timer);
			
			mainFrame.add(BorderLayout.NORTH ,Jp_Count);   //添加计数框至计数面板上方
			
			Super_Frame = new JFrame("LvXZ_MineSweep--扫雷英雄榜");
			Super_Frame.setBounds(250,250,350,300);
			Super_Frame.setLayout(new BorderLayout());
			Super_Frame.setResizable(false);
			
			Super_Panel = new JPanel();
			Super_Panel.setLayout(new GridLayout(1,1)); 
			Super_Panel.add(new Label("  姓名        级别         用时           日期"));
			Super_Frame.add(BorderLayout.NORTH ,Super_Panel);                
			Super_Area = new TextArea();
			Super_Area.setEditable(false);  //禁止编辑
			Super_Area.setFocusable(false); //禁止光标
			Super_Frame.add(Super_Area);
		
			CreateMenu();
			CreatePanel();
		}
	
		public void CreateMenu(){
			Jp_Button.setPreferredSize(new Dimension(Width[Rank],Height[Rank]));//按钮设置大小
			Jp_Button.setLayout(new GridLayout(row,col));                       //初级9*9
		}
		
		@SuppressWarnings("deprecation")
		public void CreatePanel() {
			/**********************************面板容器*************************************/	
			now_date.setMinutes(0);//获取00:00 
			now_date.setSeconds(0);  
		    //扫雷按钮的监听
			listen = new MouseListener(){
                @SuppressWarnings("static-access")
				public void mousePressed(MouseEvent e) {
					
					if(e.getClickCount() == 1)      //只要点击面板按钮，就开始计时
						time.start();

					for(int i = 0;i < MAX;i++){              
						 if(e.getSource()==Jbutton[i] ){
							 /*********************************左击--排雷--可击态******************************/
							 if( Jbutton[i].isEnabled() == true ){
						          if(e.getButton() == e.BUTTON1){       
							           if(m[P[i].x][P[i].y] == -2 ){
							                 time.stop(); Jbutton[i].setText("B");  Jbutton[i].setBackground(Color.RED);
							                 Jbutton[i].setEnabled(false);Surplus_Count--; 
							     		     JOptionPane.showMessageDialog(null, "You are loser!");
							     	   }
									   else
										     if(m[P[i].x][P[i].y] == 0){
											     Jbutton[i].setText(""); Jbutton[i].setEnabled(false);
											     Surplus_Count--; Show_Mines(m,P[i].x,P[i].y);
											 }
										     else{Jbutton[i].setText(String.valueOf(m[P[i].x][P[i].y]));
										          Jbutton[i].setEnabled(false);
										          Surplus_Count--;}
						          }
						     }
						     else   
						     /*****************************左右同时点击--智能排雷--禁止态*************************/      
							       if(e.getButton() == (e.BUTTON1 | e.BUTTON3))     
							            LR_Show_Mines(m,P[i].x,P[i].y);
							 
							 /**************************************右击--插旗**********************************/
							 if(e.getButton() == e.BUTTON3){                  
							      if(Jbutton[i].getText() == "P"){
							    	   Jbutton[i].setText(""); Jbutton[i].setEnabled(true); Jbutton[i].setBackground(null);//还原
							    	   Mines_Count++; Text_Count.setText(String.valueOf(Mines_Count));
							      }
							      else
							    	   if(Jbutton[i].isEnabled() == true ){
							               Jbutton[i].setText("P"); Jbutton[i].setBackground(Color.RED);//红色
							               Jbutton[i].setEnabled(false); Mines_Count--;
							               Text_Count.setText(String.valueOf(Mines_Count));}
							 }
						 }
					}
					
					if(Mines_Count == 0 && Surplus_Count == MinesNum[Rank]){//MAX-MinesNum[Rank]
						time.stop(); JOptionPane.showMessageDialog(null, "挑战成功!");
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
				}
				
				public void mouseClicked(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				
			};
			for(int i = 0;i<MAX;i++){                                    //按钮初始化
				Jbutton[i] = new JButton();                               //按钮设置
				Jbutton[i].addActionListener(this); 
				Jbutton[i].addMouseListener(listen);
				
                Jp_Button.add(Jbutton[i]);
			}

			/*******************************顶层容器2设置*****************************/
			mainFrame.add(Jp_Button,BorderLayout.SOUTH);                   //添加按钮至按钮面板中间
			mainFrame.pack();
		}

		//时间、重新开始的监听
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == time){
				SimpleDateFormat fm = new SimpleDateFormat("mm:ss");
				Date date = new Date(now_date.getTime()+1000);//只获取秒，赋予now_date
				now_date = date;
				Text_Timer.setText(fm.format(now_date));
			}/**********************************初始化数据复原*************************************/
			else if(e.getSource() == Restart_button){
				   //Rank 不变
				 ReSelect_Rank(Rank);   //调用Rank重新选择函数    
			}
			//0--9*9--Junior级别     1--16*16--Senior级别   2--16*30--High级别     3--30*30--Super级别
			else if(e.getSource() == JuniorItem){
				
				  Rank = 0;                     //0--9*9--Junior级别  
				  ReSelect_Rank(Rank);          
			}
			else if(e.getSource() == SeniorItem){
				 
				 Rank = 1;
				 ReSelect_Rank(Rank);
			}
			else if(e.getSource() == HighItem){
				 mainFrame.setResizable(true);
				 Rank = 2;
				 ReSelect_Rank(Rank);
			}
			else if(e.getSource() == SuperItem){
				 mainFrame.setResizable(true);
				 Rank = 3;
				 ReSelect_Rank(Rank);
			}
			else if(e.getSource() == HeroItem){
				try {
					Read_Record();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Super_Frame.setVisible(true);
			}
		}
		public void ReSelect_Rank(int R_key){
			  time.stop(); //重新开始，时间停止置为00:00
			  Text_Timer.setText("00:00");
			  
			  row = Row[R_key];      //行
			  col = Column[R_key];   //列
			  MAX = row*col;         //按钮个数
			  
			  Mines_Count = MinesNum[R_key];        //剩余地雷数复原
			  Text_Count.setText(String.valueOf(MinesNum[Rank]));
			  //System.out.println(MAX+"--"+Mines_Count+"--"+row+"--"+col);
			  
			  Surplus_Count = MAX;                  //剩余按钮复原
			  Jp_Button.removeAll();                //删除之前的按钮，便于下次重新添加
			       
			  Put_MinesArea();                      //调用地雷随机放置函数，形成新的数组
			  CreateMenu();
			  CreatePanel();                        //调用面板按钮重置函数 
			
		}
		
		//智能排雷函数
		public void LR_Show_Mines(int[][] m,int x,int y){         //0<= range <=MAX-1
		    int Mark_Count = 0;
			for(int a=x-1;a<=x+1;a++)
		        for(int b=y-1;b<=y+1;b++)
		            if(a==x && b==y )
		            	continue;	
		            else{
		            	int range = col*(a-1)+b-1;
		            	if( 0<=range && range<=MAX-1 && Jbutton[range].getText() == "P")//计算周围旗数，且避免出现边界问题
		            		Mark_Count++;
		            }
		  
			if(Mark_Count == m[x][y])
				Show_Mines(m,x,y);
			else
				Flash_Mines(m,x,y);
		}
		//周围地雷闪动函数
		public void Flash_Mines(int[][] m,int x,int y){
			for(int a=x-1;a<=x+1;a++)
		        for(int b=y-1;b<=y+1;b++)
		            if(a==x && b==y )
		            	 continue;	
		            else{
		            	 int range = col*(a-1)+b-1;
		                 if( 0<=range && range<=MAX-1 && Jbutton[range].isEnabled()){//避免出现边界问题//没有被静态
		                	 Jbutton[range].doClick(10);                             //闪动
		                 }
		            }
		}
		
		//打印周围按钮函数
		public void Show_Mines(int[][] m,int x,int y){            //0<= range <=MAX-1
			for(int a=x-1;a<=x+1;a++)
		        for(int b=y-1;b<=y+1;b++)
		            if(a==x && b==y )
		            	 continue;	
		            else{
		            	 int range = col*(a-1)+b-1;
		                 if( 0<=range && range<=MAX-1 && Jbutton[range].isEnabled()){//避免出现边界问题//没有被静态
		            	     if( m[a][b] == 0 ){
		            		     Jbutton[range].setText(""); Jbutton[range].setEnabled(false);
		            		     Surplus_Count--; Show_Mines(m,a,b);       //0按钮递归
		            		 }
		            	     else
		            		     if(m[a][b] > 0 ){
		            			     Jbutton[range].setText(String.valueOf(m[a][b]));
		            			     Jbutton[range].setEnabled(false); Surplus_Count--;}
		                 }  
		            }
		}
		
		public void Read_Record() throws IOException{
			File myFile = new File("D:/JAVA/Heroes.txt");// 字符流读取文件数据//便于打包
			
		  if(myFile.exists()){
		
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(myFile));
	        StringBuilder result=new StringBuilder();
	        String theLine=null;
	        while(( theLine=reader.readLine())!=null){
	            result.append(theLine+"\n");
	        }
	        //System.out.println(result.toString());//输出读取的字符串
	        Super_Area.setText(result.toString());
		  }
		  else{
			  JOptionPane.showMessageDialog(null, "尚未有记录！");
		  }
		}
		//保存成功数据函数
		public void Save_Data(String STR) throws IOException{
			//字符串写入文件
			
			StringBuffer STR_user = new StringBuffer(STR);
			switch(Rank){
			case 0:
				STR_user.append("   9*9   ");
				break;
			case 1:
				STR_user.append("   16*16   ");
				break;
			case 2:
				STR_user.append("   16*30   ");
				break;
			case 3:
				STR_user.append("   30*30   ");
				break;	
				
			default:break;
			}
			STR_user.append(Text_Timer.getText());//获得成功的最短时间
			STR_user.append("   ");
			Date succeed_date=new Date();   //获得成功日期
			SimpleDateFormat SM=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式规定
			STR_user.append(SM.format(succeed_date));
			STR_user.append("\r\n");
			
	        FileWriter writer = new FileWriter("D:/JAVA/Heroes.txt",true);
	        try {
	            writer.write(STR_user.toString());
	            writer.flush();
	            writer.close();
	        } catch (IOException e){
	            e.printStackTrace();
	        }
	        
		}
		
		public static void main(String[] args) {
			
			@SuppressWarnings("unused")
			MineSweep_of_LvXZ LvXZ_MineSweep  = new MineSweep_of_LvXZ();
		}

}

