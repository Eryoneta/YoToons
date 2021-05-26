package character;

public class Diretriz3D{
	private double angleZ;
		public double getAngleZ(){return angleZ;}
		public void setAngleZ(double angleZ){this.angleZ=angleZ;}
	private double angleY;
		public double getAngleY(){return angleY;}
		public void setAngleY(double angleY){this.angleY=angleY;}
	private double angleX;
		public double getAngleX(){return angleX;}
		public void setAngleX(double angleX){this.angleX=angleX;}
	public void setOrientation(double angleZ,double angleY,double angleX){setAngleZ(angleZ);setAngleY(angleY);setAngleX(angleX);}
	public Diretriz3D(double angleZ,double angleY,double angleX){setOrientation(angleZ,angleY,angleX);}
	public Diretriz3D(){}
	public static double[][]getRotationZ(double angleZ){
		return new double[][]{
				new double[]{	Math.cos(angleZ),	-Math.sin(angleZ),	0,					0	},	//COS	-SIN	0 		0
				new double[]{	Math.sin(angleZ),	Math.cos(angleZ),	0,					0	},	//SIN	COS		0		0
				new double[]{	0,					0,					1,					0	},	//0		0		1		0
				new double[]{	0,					0,					0,					1	}	//0		0		0		1
		};
	}
	public static double[][]getRotationY(double angleY){
		return new double[][]{
				new double[]{	Math.cos(angleY),	0,					Math.sin(angleY),	0	},	//COS	0		SIN		0
				new double[]{	0,					1,					0,					0	},	//0		1		0		0
				new double[]{	-Math.sin(angleY),	0,					Math.cos(angleY),	0	},	//-SIN	0		COS		0
				new double[]{	0,					0,					0,					1	}	//0		0		0		1
		};
	}
	public static double[][]getRotationX(double angleX){
		return new double[][]{
				new double[]{	1,					0,					0,					0	},	//1		0		0		0
				new double[]{	0,					Math.cos(angleX),	-Math.sin(angleX),	0	},	//0		COS		-SIN	0
				new double[]{	0,					Math.sin(angleX),	Math.cos(angleX),	0	},	//0		SIN		COS		0
				new double[]{	0,					0,					0,					1	}	//0		0		0		1
		};
	}
}
