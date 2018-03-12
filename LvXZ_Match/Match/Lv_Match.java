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

	JFrame main_Frame;                              //����������
	
	JMenuBar menuBar;                               //�˵�����
	
	JMenu fileMenu;                                 //�˵�
	JMenuItem JuniorItem;                           //�˵���
	JMenuItem HighItem;
	JMenuItem HeroesItem;                           //Ӣ�۰�                 
	JMenuItem HelpItem;                             //����
	JMenu SetItem;                                  //����
	JMenuItem Single;                               //����
	JMenuItem Double;                               //˫��
	
	
	JPanel JP_firstCard = new JPanel();             //�׿鰴ť���
	JPanel JP_secondCard = new JPanel();            //�׿����������
	JPanel JP_thirdCard = new JPanel();             //�׿���Ϸ���
	JPanel JP_forthCard = new JPanel();             //�׿鰴ť���
	JPanel JP_fifthCard = new JPanel();             //�׿�������ȡ���
	
	JButton  JB_return = new JButton("�ڲ�");        
	JButton  JB_againGame = new JButton("���¿�ʼ"); 
	JTextField Text_Timer = new JTextField("00:00");//ʱ����ʾ�ı� 

	
	JTextField JT_Model;                                //��Ϸģʽ�ı���
	JLabel JL_remainMatch;                          //ʣ�����ı���
	JLabel JL_Computer;                             //������ȡ
	JLabel JL_Player;                               //�����ȡ
	JLabel JL_Last_Computer;                        //����������ȡ
	JLabel JL_Last_Player;                          //���������ȡ
	JTextField JT_ComputerTake;                     //���������ı���
	JTextField JT_PlayerTake;                       //�������ı���
	
	JButton Button1 = new JButton("1");       
	JButton Button2 = new JButton("2");  
	JButton Button3 = new JButton("3");  
	JButton Button_Back = new JButton("�˸�");  
	JButton Button_Sure = new JButton("ȷ��");  
	
	public static int Stack_Computer[]=new int[100];//������ȡջ
	public static int Stack_Player[] = new int[100];//�����ȡջ
	public static int flag_Com = 0;                 //STACKջ������
	public static int flag_Pla = 0;                    
	public static int whoTurn = 2;                  //��˭��--2��--1�����
	public static int remainCount;                  //ʣ��������
	
	public static boolean P_C_Hard = true;          //�˻�ģʽ����ʼ--����
	public static boolean Pl_Pl = false;            //����ģʽ����ʼ--�˻�
	
	/**************timer**************/
	Timer time = new Timer(1000,this);
	Date now_date =new Date();
	
	JFrame Heroes_Frame;            //Ӣ�۰����
	JPanel Heroes_Panel;
	TextArea Heroes_Area;

	JFrame Help_Frame;              //��������
	JPanel Help_Panel;
	TextArea Help_Area;


	@SuppressWarnings("deprecation")
	public Lv_Match()  {
		/*************************������������************************/
		main_Frame = new JFrame("LvXZ_Match");
		main_Frame.setBounds(100,50,500,500);
		main_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_Frame.setVisible(true);
		main_Frame.setResizable(false);
		main_Frame.setLayout(new GridLayout(5,1,4,4));//������岼��
		main_Frame.add(JP_firstCard);
		main_Frame.add(JP_secondCard);
		main_Frame.add(JP_fifthCard);
		main_Frame.add(JP_thirdCard);
		main_Frame.add(JP_forthCard);
		
		/*************************�˵�����****************************/
		menuBar = new JMenuBar();
		main_Frame.setJMenuBar(menuBar);                  //��Ӳ˵�����������
		fileMenu = new JMenu("��Ϸ");
		HeroesItem= new JMenuItem("Ӣ�۰�");

		JuniorItem = new JMenuItem("��");          //д���б�
		HighItem = new JMenuItem("����");
		HelpItem = new JMenuItem("����");                       //����
		SetItem = new JMenu("����");                          //����
		Single = new JMenuItem("����");
		Double = new JMenuItem("˫��");
		
		menuBar.add(fileMenu);                         //����б����˵�
		fileMenu.add(JuniorItem);
		fileMenu.add(HighItem);
		menuBar.add(SetItem);
		SetItem.add(Single);
		SetItem.add(Double);
		menuBar.add(HeroesItem);
		menuBar.add(HelpItem);
		
		JuniorItem.addActionListener(this);           //����
		HighItem.addActionListener(this);
		Single.addActionListener(this); 
		Double.addActionListener(this); 
		HeroesItem.addActionListener(this); 
		HelpItem.addActionListener(this);  
		
		/*************************�����������************************/
		JP_firstCard.setLayout(new GridLayout(1,3,4,4));                      //����1��3��
		JP_firstCard.setBorder(new EmptyBorder(4,4,4,4)); 
		JP_firstCard.add(JB_return);      JB_return.addActionListener(this);  //��ť��ӣ�����
		JP_firstCard.add(JB_againGame);JB_againGame.addActionListener(this);  
		JP_firstCard.add(new JLabel());
		JP_firstCard.add(Text_Timer);
		Text_Timer.setFocusable(false);                                       //ȡ�����
		JB_return.setBackground(Color.YELLOW);
		JB_againGame.setBackground(Color.RED);
		
		JP_secondCard.setLayout(new GridLayout(1,3,4,4));                      //����1��3��
		JP_secondCard.setBorder(new EmptyBorder(4,4,4,4)); 
		JT_Model = new JTextField("�˻�����ģʽ");                              //��ʼ--�˻�����ģʽ
		JT_Model.setEditable(false);
		JL_remainMatch = new JLabel();
		remainCount = 20+(int)(Math.random()*30);                             //�������20~50
		JL_remainMatch.setText("�ܻ������:"+remainCount);
		JL_remainMatch.setBorder(BorderFactory.createLineBorder(Color.BLACK));//�����߿��ɫ
		JP_secondCard.add(JT_Model);                        
		JP_secondCard.add(JL_remainMatch);               
		JP_secondCard.add(new JLabel());
		

		JP_thirdCard.setLayout(new GridLayout(1,5,2,2));                      //����1��5��
		JP_thirdCard.setBorder(new EmptyBorder(8,8,8,8)); 
		JL_Computer = new JLabel("������ȡ");
		JL_Player  = new JLabel("�����ȡ");
		JT_PlayerTake = new JTextField("δ��ʼ");
		JT_ComputerTake = new JTextField("δ��ʼ");
		JP_thirdCard.add(JL_Computer);
		JP_thirdCard.add(JT_ComputerTake);
		JT_ComputerTake.setFocusable(false); 
		JP_thirdCard.add(new JLabel());
		JP_thirdCard.add(JL_Player);
		JP_thirdCard.add(JT_PlayerTake);
		JT_PlayerTake.setFocusable(false);                        //ȡ�����
		
		JP_forthCard.setLayout(new GridLayout(1,5,4,4));          //����1��5��
		JP_forthCard.setBorder(new EmptyBorder(4,4,4,4));         //�߿�����
		JP_forthCard.add(Button1);Button1.addActionListener(this);//��ť��ӣ�����
		JP_forthCard.add(Button2);Button2.addActionListener(this);
		JP_forthCard.add(Button3);Button3.addActionListener(this);
		JP_forthCard.add(Button_Back);Button_Back.addActionListener(this);
		JP_forthCard.add(Button_Sure);Button_Sure.addActionListener(this);
		Button_Back.setBackground(Color.LIGHT_GRAY);
		Button_Sure.setBackground(Color.LIGHT_GRAY);
	
		JP_fifthCard.setLayout(new GridLayout(1,5,4,4));
		JP_fifthCard.setBorder(new EmptyBorder(8,8,8,8));         //�߿�����
		
		JP_fifthCard.add(new JLabel("������ȡ"));
		JL_Last_Computer = new JLabel("δ��ʼ");
		JP_fifthCard.add(JL_Last_Computer);
		JP_fifthCard.add(new JLabel());
		JP_fifthCard.add(new JLabel("������ȡ"));
		JL_Last_Player = new JLabel("δ��ʼ");
		JP_fifthCard.add(JL_Last_Player);
		
		main_Frame.pack();
		
		now_date.setMinutes(0);                                  //��ȡ00:00 
		now_date.setSeconds(0);  
		
		Stack_Computer[0] = 0;                                   //ջ�ĳ�ʼ��
		Stack_Player[0] = 0;
		
		/**************************Ӣ�۰�********************************/
		Heroes_Frame = new JFrame("LvXZ_Match--Ӣ�۰�");
		Heroes_Frame.setBounds(250,250,350,300);
		Heroes_Frame.setLayout(new BorderLayout());
		Heroes_Frame.setResizable(false);
		
		Heroes_Panel = new JPanel();
		Heroes_Panel.setLayout(new GridLayout(1,1)); 
		Heroes_Panel.add(new Label("  ����            ����                      ��ʱ                   ����"));
		Heroes_Frame.add(BorderLayout.NORTH ,Heroes_Panel);                
		Heroes_Area = new TextArea();
		Heroes_Area.setEditable(false);  //��ֹ�༭
		Heroes_Area.setFocusable(false); //��ֹ���
		Heroes_Frame.add(Heroes_Area);
		
		/**************************����********************************/
		Help_Frame = new JFrame("LvXZ_Match--����");
		Help_Frame.setBounds(350,250,350,300);
		Help_Frame.setLayout(new BorderLayout());
		Help_Frame.setResizable(false);
		Help_Panel = new JPanel();
		Help_Panel.setLayout(new GridLayout(1,1)); 
		Help_Frame.add(BorderLayout.NORTH ,Help_Panel);                
		Help_Area = new TextArea();
		Help_Area.setEditable(false);  //��ֹ�༭
		Help_Area.setFocusable(false); //��ֹ���
		Help_Frame.add(Help_Area);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == time){
			SimpleDateFormat fm = new SimpleDateFormat("mm:ss");
			Date date = new Date(now_date.getTime()+1000);//ֻ��ȡ�룬����now_date
			now_date = date;
			Text_Timer.setText(fm.format(now_date));
		}else if(e.getActionCommand()=="1" || e.getActionCommand()=="2" || e.getActionCommand()=="3"){         
			      time.start();//ֻҪ�����尴ť���Ϳ�ʼ��ʱ
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
	            if(whoTurn == 2)    manTake_2();           //���2�û������
	            else    manTake_1();                       //���1�û������
	        else
	        	manTake_2();                               //�˻�	
		}
		else if(e.getSource() == JB_return){                //�ڲ�����
			if(flag_Com>0 && flag_Pla>0) {   
				return_ComputerTake();
				return_PlayerTake();
			}
			else   JOptionPane.showMessageDialog(null, "�Ѿ��ڲ�����ʼ��...");
		}
		else if(e.getSource() == JB_againGame){             //���¿�ʼ����
			AgainGame();  
		}
		else if(e.getSource() == JuniorItem){               //ģʽ���л�����AgainGame()
			P_C_Hard = false;
			JT_Model.setText("�˻���ģʽ");  
			AgainGame();
		}
		else if(e.getSource() == HighItem){ 
			P_C_Hard = true;
			JT_Model.setText("�˻�����ģʽ"); 
			AgainGame();
		}
		else if(e.getSource() == HeroesItem){  //Ӣ�۰�
			try {
				Read_Record();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Heroes_Frame.setVisible(true);
		}
		else if(e.getSource() == HelpItem){  //����
			Help_Area.setText("��Ϸ˵�����������20~50�����˫��������ȡ��ÿ����ȡ������������3�����õ����һ���Ļ�ʤ��");
			Help_Frame.setVisible(true);
		}
		else if(e.getSource() == Single){  //����--�ָ�������ѡ��
			Pl_Pl = false;
			JB_return.setEnabled(true); 
			whoTurn = 2;
			JuniorItem.setEnabled(true);
			HighItem.setEnabled(true);
			if(P_C_Hard)
				JT_Model.setText("�˻�����ģʽ"); //����ԭ�ȵ�ѡ��
			else
				JT_Model.setText("�˻���ģʽ");  
			AgainGame();
	
		}
		else if(e.getSource() == Double){   //˫��--ȡ��������ѡ��
			Pl_Pl = true;
			JB_return.setEnabled(false);    //����֮�䣬�����޻�
			whoTurn = 1;
			JuniorItem.setEnabled(false);
			HighItem.setEnabled(false);
			JT_Model.setText("����PKģʽ");  
			AgainGame();
		}
	
	}
	//���뺯��
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
		remainCount = 20+(int)(Math.random()*30);       //�������20~50
		JL_remainMatch.setText("�ܻ������:"+remainCount);
		
		time.stop();                                    //ʱ��ֹͣ��Ϊ00:00
		now_date.setMinutes(0);                         //��ȡ00:00 
		now_date.setSeconds(0); 
		Text_Timer.setText("00:00");                    //���³�ʼ��ʱ��
		JT_PlayerTake.setText("δ��ʼ");
		JT_ComputerTake.setText("δ��ʼ");
		JL_Last_Computer.setText("δ��ʼ");
		JL_Last_Player.setText("δ��ʼ");
		flag_Pla=0;                                     //ջ���³�ʼ��
		flag_Com=0;
	}
	
	public void manTake_2(){
		
		if(Pl_Pl == true)
		     JL_Last_Computer.setText(String.valueOf(Stack_Computer[flag_Com]));
		
		JT_ComputerTake.setText("");
		
		int y = Integer.parseInt(JT_PlayerTake.getText()); //���ַ���ת��Ϊ����
		flag_Pla++;
		Stack_Player[flag_Pla] = y;                        //������putջ
		
		if(Pl_Pl == true)
			JL_Last_Player.setText(String.valueOf(Stack_Player[flag_Pla-1]));
		else
		    JL_Last_Player.setText(String.valueOf(Stack_Player[flag_Pla])); 
		
		//JT_PlayerTakeֻ����1���ַ������迼����1~3
		remainCount = remainCount - y;
		JL_remainMatch.setText("ʣ��������:"+remainCount);
		
		if(remainCount == 0)   displayResult();
		else{
			whoTurn = 1;
			if( !Pl_Pl )     computerTake();                //�˻�ģʽ--���������
		}	
	}
	
	public void manTake_1(){
		
		if(Pl_Pl == true)
		     JL_Last_Player.setText(String.valueOf(Stack_Player[flag_Pla]));
		
		JT_PlayerTake.setText("");
		
		int x = Integer.parseInt(JT_ComputerTake.getText()); //���ַ���ת��Ϊ����
		flag_Com++;
		Stack_Computer[flag_Com] = x;                        //������putջ
		
		if(Pl_Pl == true)
			 JL_Last_Computer.setText(String.valueOf(Stack_Computer[flag_Com-1]));
		else
		    JL_Last_Computer.setText(String.valueOf(Stack_Computer[flag_Com])); 
		
		//JT_PlayerTakeֻ����1���ַ������迼����1~3
		remainCount = remainCount - x;
		JL_remainMatch.setText("ʣ��������:"+remainCount);
		
		if(remainCount == 0)   displayResult();
		else   whoTurn = 2;
		
	}
	
	public void computerTake(){
		
		JL_Last_Computer.setText(String.valueOf(Stack_Computer[flag_Com])); 
		JT_PlayerTake.setText("");
		int x;
		
		if(P_C_Hard == true){
			if(remainCount%4 == 0)
				x = 1+(int)(Math.random()*3);   //�����ȡ1~3
			else
				x = remainCount%4 ;             //��ȡ4������
		}
		else{
			x = 1+(int)(Math.random()*3);
			if(remainCount > 0 && remainCount <= 8) //��ģʽ�£���΢����Ѷ�
				if(remainCount%4 == 0)
					x = 1+(int)(Math.random()*3);   //�����ȡ1~3
				else
					x = remainCount%4 ;             //��ȡ4������     
		}
		
		flag_Com++;
		Stack_Computer[flag_Com] = x;
		
		JT_ComputerTake.setText(String.valueOf(x));
		remainCount = remainCount - x;
		JL_remainMatch.setText("ʣ��������:"+remainCount);
		
		if(remainCount == 0)   displayResult();
		else    whoTurn = 2;
	
	}
	
	public void return_PlayerTake(){
		/*********************************��ҷ���***************************/
		remainCount = remainCount + Stack_Player[flag_Pla];        
		flag_Pla--;
		if(flag_Pla == 0){
			JT_PlayerTake.setText("0");   JL_Last_Player.setText("0");  
		}
		else
		    JL_Last_Player.setText(String.valueOf(Stack_Player[flag_Pla])); //��ʾ�ϴ����ѡ��
		
		JL_remainMatch.setText("ʣ��������:"+remainCount);
	}
	
	public void return_ComputerTake(){
		/*********************************���Է���***************************/
		remainCount = remainCount + Stack_Computer[flag_Com]; 
		flag_Com--;
		if(flag_Com == 0){
			JT_ComputerTake.setText("0");  JL_Last_Computer.setText("0");
		}
		else{
		   JT_ComputerTake.setText(String.valueOf(Stack_Computer[flag_Com]));
		   JL_Last_Computer.setText(String.valueOf(Stack_Computer[flag_Com-1]));//��ʾ�ϴε���ѡ��
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
		else{
			time.stop(); 
            if(Pl_Pl == true ){
            	JOptionPane.showMessageDialog(null, "Player 1 win!");
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
            else
			    JOptionPane.showMessageDialog(null, "Computer win!");
		}
	}

	public void Read_Record() throws IOException{
		File myFile = new File("D:/JAVA/Match_Heroes.txt");// �ַ�����ȡ�ļ�����//���ڴ��
	
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
		  JOptionPane.showMessageDialog(null, "��δ�м�¼��");
	  
	}
	//����ɹ����ݺ���
	public void Save_Data(String STR) throws IOException{
		//�ַ���д���ļ�
		StringBuffer STR_user = new StringBuffer(STR);
		
		if(Pl_Pl == true){
			STR_user.append("   ����PKģʽ    ");
		}
		else
			if(P_C_Hard == true){
				STR_user.append("   �˻�����ģʽ    ");
			}
			else{
				STR_user.append("   �˻���ģʽ    ");
			}
	
		STR_user.append(Text_Timer.getText());//��óɹ������ʱ��
		STR_user.append("   ");
		Date succeed_date=new Date();         //��óɹ�����
		SimpleDateFormat SM=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//ʱ���ʽ�涨
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
