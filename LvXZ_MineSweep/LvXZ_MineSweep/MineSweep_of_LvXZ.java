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
	  
	   public static final int Width[] = {300,680,1350,1300};   //Frame�߿�--�ȼ���ʼ��
	   public static final int Height[] ={340,670,670,1300};
    
	   public static int Mines_Count = MinesNum[Rank];
	   public static int Surplus_Count = MAX;     //ʣ�ఴť����

		JFrame mainFrame;                         //��������--�ܴ��ڽ���
		JMenuBar menuBar;                         //�˵�����
		
		JMenu fileMenu;                           //�˵�
		JMenu HeroLevel_Menu;
		
		JMenuItem JuniorItem;                     //�˵���
		JMenuItem SeniorItem;
		JMenuItem HighItem;
		JMenuItem SuperItem;
		JMenuItem HeroItem;

		JPanel Jp_Button = new JPanel();          //��ť���
		JPanel Jp_Count = new JPanel();           //�������
		
		JTextField Text_Count;                    //����ʣ�����ı�
		JTextField Text_Timer;                    //ʱ����ʾ�ı�
		
		JButton Jbutton[] = new JButton[2500];    //����������
		
		MouseListener listen ;
		JButton Restart_button;
		
		/**************timer**************/
		Timer time = new Timer(1000,this);
		Date now_date =new Date();  
		
		JFrame Super_Frame;
		JPanel Super_Panel;
		TextArea Super_Area;
	
		
		MineSweep_of_LvXZ(){
			/*************************��������1����************************/
			mainFrame = new JFrame("LvXZ_MineSweep--ɨ��");
			mainFrame.setBounds(50,0,320,320);
			mainFrame.setLayout(new BorderLayout());
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setVisible(true);
			mainFrame.setResizable(false);
			
			/*************************�˵�����****************************/
			menuBar = new JMenuBar();
			mainFrame.setJMenuBar(menuBar);                  //��Ӳ˵�����������
			
			fileMenu = new JMenu("��Ϸ");
			HeroLevel_Menu = new JMenu("Ӣ�۰�");

			JuniorItem = new JMenuItem("����9x9");          //д���б�
			SeniorItem = new JMenuItem("�м�16x16");
			HighItem = new JMenuItem("�߼�16x30");
			SuperItem = new JMenuItem("���񲡼�30x30");
			HeroItem = new JMenuItem("Ӣ������");
			
			menuBar.add(fileMenu);                         //����б����˵�
			fileMenu.add(JuniorItem);
			fileMenu.add(SeniorItem);
			fileMenu.add(HighItem);
			fileMenu.add(SuperItem);
			menuBar.add(HeroLevel_Menu);
			HeroLevel_Menu.add(HeroItem);
			
			JuniorItem.addActionListener(this);           //����
			SeniorItem.addActionListener(this);
			HighItem.addActionListener(this);
			SuperItem.addActionListener(this);
			HeroItem.addActionListener(this);
			
		    Text_Count = new JTextField(String.valueOf(MinesNum[Rank]));     //���׳�ʼ��
			Text_Timer = new JTextField("00:00");
			Restart_button = new JButton("���¿�ʼ");
			
			Jp_Count.setLayout(new GridLayout(1,5));                         //�����������
			Jp_Count.add(Text_Count);
			Jp_Count.add(new Label("  P:����"));
			Jp_Count.add(Restart_button);
			Restart_button.addActionListener(this);
			Jp_Count.add(new Label("  B:ը��"));
			Jp_Count.add(Text_Timer);
			
			mainFrame.add(BorderLayout.NORTH ,Jp_Count);   //��Ӽ���������������Ϸ�
			
			Super_Frame = new JFrame("LvXZ_MineSweep--ɨ��Ӣ�۰�");
			Super_Frame.setBounds(250,250,350,300);
			Super_Frame.setLayout(new BorderLayout());
			Super_Frame.setResizable(false);
			
			Super_Panel = new JPanel();
			Super_Panel.setLayout(new GridLayout(1,1)); 
			Super_Panel.add(new Label("  ����        ����         ��ʱ           ����"));
			Super_Frame.add(BorderLayout.NORTH ,Super_Panel);                
			Super_Area = new TextArea();
			Super_Area.setEditable(false);  //��ֹ�༭
			Super_Area.setFocusable(false); //��ֹ���
			Super_Frame.add(Super_Area);
		
			CreateMenu();
			CreatePanel();
		}
	
		public void CreateMenu(){
			Jp_Button.setPreferredSize(new Dimension(Width[Rank],Height[Rank]));//��ť���ô�С
			Jp_Button.setLayout(new GridLayout(row,col));                       //����9*9
		}
		
		@SuppressWarnings("deprecation")
		public void CreatePanel() {
			/**********************************�������*************************************/	
			now_date.setMinutes(0);//��ȡ00:00 
			now_date.setSeconds(0);  
		    //ɨ�װ�ť�ļ���
			listen = new MouseListener(){
                @SuppressWarnings("static-access")
				public void mousePressed(MouseEvent e) {
					
					if(e.getClickCount() == 1)      //ֻҪ�����尴ť���Ϳ�ʼ��ʱ
						time.start();

					for(int i = 0;i < MAX;i++){              
						 if(e.getSource()==Jbutton[i] ){
							 /*********************************���--����--�ɻ�̬******************************/
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
						     /*****************************����ͬʱ���--��������--��ֹ̬*************************/      
							       if(e.getButton() == (e.BUTTON1 | e.BUTTON3))     
							            LR_Show_Mines(m,P[i].x,P[i].y);
							 
							 /**************************************�һ�--����**********************************/
							 if(e.getButton() == e.BUTTON3){                  
							      if(Jbutton[i].getText() == "P"){
							    	   Jbutton[i].setText(""); Jbutton[i].setEnabled(true); Jbutton[i].setBackground(null);//��ԭ
							    	   Mines_Count++; Text_Count.setText(String.valueOf(Mines_Count));
							      }
							      else
							    	   if(Jbutton[i].isEnabled() == true ){
							               Jbutton[i].setText("P"); Jbutton[i].setBackground(Color.RED);//��ɫ
							               Jbutton[i].setEnabled(false); Mines_Count--;
							               Text_Count.setText(String.valueOf(Mines_Count));}
							 }
						 }
					}
					
					if(Mines_Count == 0 && Surplus_Count == MinesNum[Rank]){//MAX-MinesNum[Rank]
						time.stop(); JOptionPane.showMessageDialog(null, "��ս�ɹ�!");
						String STR = JOptionPane.showInputDialog("��������������");
						if( STR==null ||  STR.equals(""))
							JOptionPane.showMessageDialog(null, "������ȡ����");
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
			for(int i = 0;i<MAX;i++){                                    //��ť��ʼ��
				Jbutton[i] = new JButton();                               //��ť����
				Jbutton[i].addActionListener(this); 
				Jbutton[i].addMouseListener(listen);
				
                Jp_Button.add(Jbutton[i]);
			}

			/*******************************��������2����*****************************/
			mainFrame.add(Jp_Button,BorderLayout.SOUTH);                   //��Ӱ�ť����ť����м�
			mainFrame.pack();
		}

		//ʱ�䡢���¿�ʼ�ļ���
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == time){
				SimpleDateFormat fm = new SimpleDateFormat("mm:ss");
				Date date = new Date(now_date.getTime()+1000);//ֻ��ȡ�룬����now_date
				now_date = date;
				Text_Timer.setText(fm.format(now_date));
			}/**********************************��ʼ�����ݸ�ԭ*************************************/
			else if(e.getSource() == Restart_button){
				   //Rank ����
				 ReSelect_Rank(Rank);   //����Rank����ѡ����    
			}
			//0--9*9--Junior����     1--16*16--Senior����   2--16*30--High����     3--30*30--Super����
			else if(e.getSource() == JuniorItem){
				
				  Rank = 0;                     //0--9*9--Junior����  
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
			  time.stop(); //���¿�ʼ��ʱ��ֹͣ��Ϊ00:00
			  Text_Timer.setText("00:00");
			  
			  row = Row[R_key];      //��
			  col = Column[R_key];   //��
			  MAX = row*col;         //��ť����
			  
			  Mines_Count = MinesNum[R_key];        //ʣ���������ԭ
			  Text_Count.setText(String.valueOf(MinesNum[Rank]));
			  //System.out.println(MAX+"--"+Mines_Count+"--"+row+"--"+col);
			  
			  Surplus_Count = MAX;                  //ʣ�ఴť��ԭ
			  Jp_Button.removeAll();                //ɾ��֮ǰ�İ�ť�������´��������
			       
			  Put_MinesArea();                      //���õ���������ú������γ��µ�����
			  CreateMenu();
			  CreatePanel();                        //������尴ť���ú��� 
			
		}
		
		//�������׺���
		public void LR_Show_Mines(int[][] m,int x,int y){         //0<= range <=MAX-1
		    int Mark_Count = 0;
			for(int a=x-1;a<=x+1;a++)
		        for(int b=y-1;b<=y+1;b++)
		            if(a==x && b==y )
		            	continue;	
		            else{
		            	int range = col*(a-1)+b-1;
		            	if( 0<=range && range<=MAX-1 && Jbutton[range].getText() == "P")//������Χ�������ұ�����ֱ߽�����
		            		Mark_Count++;
		            }
		  
			if(Mark_Count == m[x][y])
				Show_Mines(m,x,y);
			else
				Flash_Mines(m,x,y);
		}
		//��Χ������������
		public void Flash_Mines(int[][] m,int x,int y){
			for(int a=x-1;a<=x+1;a++)
		        for(int b=y-1;b<=y+1;b++)
		            if(a==x && b==y )
		            	 continue;	
		            else{
		            	 int range = col*(a-1)+b-1;
		                 if( 0<=range && range<=MAX-1 && Jbutton[range].isEnabled()){//������ֱ߽�����//û�б���̬
		                	 Jbutton[range].doClick(10);                             //����
		                 }
		            }
		}
		
		//��ӡ��Χ��ť����
		public void Show_Mines(int[][] m,int x,int y){            //0<= range <=MAX-1
			for(int a=x-1;a<=x+1;a++)
		        for(int b=y-1;b<=y+1;b++)
		            if(a==x && b==y )
		            	 continue;	
		            else{
		            	 int range = col*(a-1)+b-1;
		                 if( 0<=range && range<=MAX-1 && Jbutton[range].isEnabled()){//������ֱ߽�����//û�б���̬
		            	     if( m[a][b] == 0 ){
		            		     Jbutton[range].setText(""); Jbutton[range].setEnabled(false);
		            		     Surplus_Count--; Show_Mines(m,a,b);       //0��ť�ݹ�
		            		 }
		            	     else
		            		     if(m[a][b] > 0 ){
		            			     Jbutton[range].setText(String.valueOf(m[a][b]));
		            			     Jbutton[range].setEnabled(false); Surplus_Count--;}
		                 }  
		            }
		}
		
		public void Read_Record() throws IOException{
			File myFile = new File("D:/JAVA/Heroes.txt");// �ַ�����ȡ�ļ�����//���ڴ��
			
		  if(myFile.exists()){
		
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(myFile));
	        StringBuilder result=new StringBuilder();
	        String theLine=null;
	        while(( theLine=reader.readLine())!=null){
	            result.append(theLine+"\n");
	        }
	        //System.out.println(result.toString());//�����ȡ���ַ���
	        Super_Area.setText(result.toString());
		  }
		  else{
			  JOptionPane.showMessageDialog(null, "��δ�м�¼��");
		  }
		}
		//����ɹ����ݺ���
		public void Save_Data(String STR) throws IOException{
			//�ַ���д���ļ�
			
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
			STR_user.append(Text_Timer.getText());//��óɹ������ʱ��
			STR_user.append("   ");
			Date succeed_date=new Date();   //��óɹ�����
			SimpleDateFormat SM=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ʱ���ʽ�涨
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

