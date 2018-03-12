package LvXZ_MineSweep;


//坐标
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
	
	//0--9*9--Junior级别     1--16*16--Senior级别   2--16*30--High级别     3--30*30--Super级别
	public static int Rank = 0;
	public static final int Row[] =      {9,16,16,30};           //不同级别行
	public static final int Column[] =   {9,16,30,30};           //不同级别列
	public static final int MinesNum[] = {10,40,99,180};         //不同级别的地雷数
	
	//初始化为0--9*9--Junior级别
	public static int row = Row[Rank];     //行
	public static int col = Column[Rank];  //列
	public static int MAX = row*col;       //按钮个数

	public int m[][] = new int[50][50];
	public Point P[] = new Point[2500];    //假设存在最多
	
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
		
		        //按钮矩阵初始化
				int count =0;
				for(int i=0;i<=row+1;i++)
				    for(int j=0;j<=col+1;j++){
				        if(i==0 || i==row+1 || j==0 || j==col+1)
				            m[i][j] = -1;                           //边框
				        else{
				              m[i][j] = 0;                          //默认首先都无雷
				              P[count] = new Point(i,j);				       
				              count++;
				        }
				    }
				
		        //埋雷
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
				    m[P[temp].x][P[temp].y] = -2;//地雷		 
				}
				
				//计算雷数
				for(int i=1;i<=row;i++)
				   for(int j=1;j<=col;j++)
				       if(m[i][j] == 0)
				    	   Count_Mines(m,i,j);  
				
				for(int i=0;i<=row+1;i++){
					for(int j=0;j<=col+1;j++){
						if(m[i][j] == -2)  System.out.print("雷  ");
						else
							if(m[i][j] == -1)  System.out.print("* ");
							else  System.out.print(m[i][j]+" ");
					}
					System.out.println();
				}
	}

	//计算雷数函数
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
