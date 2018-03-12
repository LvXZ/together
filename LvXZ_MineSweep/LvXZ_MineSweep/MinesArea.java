package LvXZ_MineSweep;


//����
class Point{
	protected int x;
	protected int y;
	
	public Point() {super();}
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
}

class MinesArea extends Point{
	
	//0--9*9--Junior����     1--16*16--Senior����   2--16*30--High����     3--30*30--Super����
	public static int Rank = 0;
	public static final int Row[] =      {9,16,16,30};           //��ͬ������
	public static final int Column[] =   {9,16,30,30};           //��ͬ������
	public static final int MinesNum[] = {10,40,99,180};         //��ͬ����ĵ�����
	
	//��ʼ��Ϊ0--9*9--Junior����
	public static int row = Row[Rank];     //��
	public static int col = Column[Rank];  //��
	public static int MAX = row*col;       //��ť����

	public int m[][] = new int[50][50];
	public Point P[] = new Point[2500];    //����������
	
	public MinesArea() { 
		super();
		Put_MinesArea();
	}

	public MinesArea(int[][] m, Point[] p) {
		super();
		this.m = m;
		P = p;
	}

	public void Put_MinesArea() {
		
		        //��ť�����ʼ��
				int count =0;
				for(int i=0;i<=row+1;i++)
				    for(int j=0;j<=col+1;j++){
				        if(i==0 || i==row+1 || j==0 || j==col+1)
				            m[i][j] = -1;                           //�߿�
				        else{
				              m[i][j] = 0;                          //Ĭ�����ȶ�����
				              P[count] = new Point(i,j);				       
				              count++;
				        }
				    }
				
		        //����
				int N=MAX, temp;
				int A[] = new int[N];
				for(int i=0;i<MAX;i++)
				    A[i]=i;

				for(int i=0;i<MinesNum[Rank];i++){
				    int flag = (int)(Math.random()*N);
				    //System.out.println(" "+flag+"--"+P[flag].x+"--"+P[flag].y);
				    temp = A[flag]; 
				    A[flag] = A[N-1];
				    N--;	
				    m[P[temp].x][P[temp].y] = -2;//����		 
				}
				
				//��������
				for(int i=1;i<=row;i++)
				   for(int j=1;j<=col;j++)
				       if(m[i][j] == 0)
				    	   Count_Mines(m,i,j);  
				
				for(int i=0;i<=row+1;i++){
					for(int j=0;j<=col+1;j++){
						if(m[i][j] == -2)  System.out.print("��  ");
						else
							if(m[i][j] == -1)  System.out.print("* ");
							else  System.out.print(m[i][j]+" ");
					}
					System.out.println();
				}
	}

	//������������
	public static void Count_Mines(int[][] m,int x,int y){
	    for(int a=x-1;a<=x+1;a++)
	        for(int b=y-1;b<=y+1;b++){
	            if(a==x && b==y )
	            	continue;	
	            else
	            	if( m[a][b] == -2)	m[x][y]++;
            }  
	}
	
}
