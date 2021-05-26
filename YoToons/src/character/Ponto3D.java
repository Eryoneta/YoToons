package character;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Arrays;
public class Ponto3D{
//	private Ponto pontoFinal;
//		public Ponto getPontoFinal(){return pontoFinal;}
//		public void setPontoFinal(Ponto pontoFinal){this.pontoFinal=pontoFinal;}
	private double x=0;
		public double getX(){return x;}
		public void setX(double x){this.x=x;}
	private double y=0;
		public double getY(){return y;}
		public void setY(double y){this.y=y;}
	private double z=0;
		public double getZ(){return z;}
		public void setZ(double z){this.z=z;}
	public void setLocation(double x,double y,double z){setX(x);setY(y);setZ(z);}
	public void setLocation(Ponto3D ponto){setX(ponto.x);setY(ponto.y);setZ(ponto.z);}
	
	public Ponto3D(){}
	public Ponto3D(double x,double y,double z){setLocation(x,y,z);}
	public Ponto3D(Ponto3D ponto){setLocation(ponto.getX(),ponto.getY(),ponto.getZ());}
	
//	public double getDistance(Ponto ponto){return new Point2D.Double(this.getX(),this.getY()).distance(new Point2D.Double(ponto.getX(),ponto.getY()));}
	
	public static double[][]getTranslation(double distanceX,double distanceY,double distanceZ){
		return new double[][]{
				new double[]{	1,	0,	0,	distanceX	},	//1		0		0 		x
				new double[]{	0,	1,	0,	distanceY	},	//0		1		0		0
				new double[]{	0,	0,	1,	distanceZ	},	//0		0		1		0
				new double[]{	0,	0,	0,	1			}	//0		0		0		1
		};
	}
	public double[][]getPointMatrix(){
		return new double[][]{
				new double[]{1,0,0,getX()},
				new double[]{0,1,0,getY()},
				new double[]{0,0,1,getZ()},
				new double[]{0,0,0,1}
		};
	}
	public static Ponto3D getPonto3D(double[][]matrix){
		matrix=transform(matrix,new double[][]{
				new double[]{1},
				new double[]{1},
				new double[]{1},
				new double[]{1}
		});
		return new Ponto3D(matrix[0][0],matrix[1][0],matrix[2][0]);
	}
	public static double[][]transform(double[][]...Ms){
		if(Ms.length==1)return Ms[0];
		final double[][]M1=Ms[0];
		final double[][]M2=Ms[1];
		final int colsM1=M1[0].length;
		final int rowsM1=M1.length;
		final int colsM2=M2[0].length;
		final int rowsM2=M2.length;
		if(colsM1!=rowsM2&&rowsM1!=colsM2)return null;
		final double[][]matrix=new double[rowsM1][colsM2];
		for(int r=0;r<rowsM1;r++)for(int c=0;c<colsM2;c++){
			matrix[r][c]=0;
			for(int i=0;i<rowsM2;i++)matrix[r][c]=(double)(matrix[r][c]+(double)(M1[r][i]*M2[i][c]));
		}
		if(Ms.length==2)return matrix;
		return transform(matrix,transform(Arrays.copyOfRange(Ms,2,Ms.length)));		//POTENCIALMENTE PESADO!
	}
	public Ponto3D scale(Ponto3D ponto,double escala){return new Ponto3D(ponto.getX()*escala,ponto.getY()*escala,ponto.getZ()*escala);}

	//ATENÇÃO PARA MANTER TUDO EM DOUBLE: OPERAÇÕES DE + OU - O MUDAM PARA INTEGER!(OU NÃO?)
	
//	public Ponto3D getRelativePoint3D(Diretriz3D diretriz,double distance){			//RETORNA PONTO COM DISTÂNCIA E ÂNGULO RELATIVO A ESTE
		
//	}
//	public static double distance=2;
//	public static double farDistance=4;
//	public static double width=600;
//	public static double height=600;
//	public static double[][]getProjetion(){
//		return new double[][]{
//				new double[]{	2*distance/width,	0,	0,	0			},	//1		0		0 		0
//				new double[]{	0,	2*distance/height,	0,	0			},	//0		1		0		0
//				new double[]{	0,	0,	(distance+farDistance)/(distance-farDistance),		(2*distance*farDistance)/(distance-farDistance)},	//0		0		1		z
//				new double[]{	0,	0,	-1,	0			}	//0		0		0		1
//		};
//	}
	public Ponto getPonto2D(){	//RETIRA Z, PROJETANDO O 3D NO 2D
		
//		final double[][]matrix=transform(getPointMatrix(),getProjetion());
//		return new Ponto(getX()+matrix[0][0],getY()+matrix[0][1]);
		
//		final double distance=0.2;
//		final double d=1/(distance-getZ());//Z PODE SER MUITO GRANDE, HENCE A "EXPLOSÃO"(DEVE SER MENOR QUE 1)
//		final double[][]P=new double[][]{
//				new double[]{d,0,0,0},	//d		0		0 
//				new double[]{0,d,0,0},	//0		d		0
//				new double[]{0,0,0,0},	//0		0		0
//				new double[]{0,0,0,0}	//0		0		0
//		};
//		final double[][]matrix=transform(getPointMatrix(),P);
//		return new Ponto(getX()+matrix[0][0],getY()+matrix[0][1]);
		
		return new Ponto(getX(),getY());
	}
//	public double getAngle(Ponto ponto){			//DEGREES(0-360), COMEÇA NA ESQUERDA E SEGUE SENTIDO HORÁRIO, O CENTRO SENDO ESTE PONTO
//		return ((Math.atan2(getY()-ponto.getY(),getX()-ponto.getX())*180/Math.PI)%360)-90;
//	}
	public void draw(Graphics2D imagemEdit){
		final Color oldCor=imagemEdit.getColor();
		final Stroke oldGrossura=imagemEdit.getStroke();
		imagemEdit.setColor(Color.RED);
		imagemEdit.setStroke(new BasicStroke(2f));
//		imagemEdit.draw(getRelativeForm(local));
		
		imagemEdit.fillOval((int)getX(),(int)getY(),1,1);
		
		imagemEdit.setColor(oldCor);
		imagemEdit.setStroke(oldGrossura);
	}
}
