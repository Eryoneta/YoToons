package character;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class Osso{
	private String nome;
		public String getNome(){return nome;}
		public void setNome(String nome){this.nome=nome;}
	private Esqueleto esqueleto;	//NECESSÁRIO?
		public Esqueleto getEsqueleto(){return esqueleto;}
	private Ponto3D relativeLocal=new Ponto3D();
		public double getRelativeX(){return relativeLocal.getX();}
		public double getRelativeY(){return relativeLocal.getY();}
		public double getRelativeZ(){return relativeLocal.getZ();}
		public Ponto3D getRelativeLocation(){return relativeLocal;}
		public void setRelativeLocation(Ponto3D relativeLocal){this.relativeLocal=relativeLocal;update();}
		public void setRelativeLocation(double x,double y,double z){relativeLocal.setLocation(x,y,z);update();}
	private Ponto3D absoluteLocal=new Ponto3D();
		public double getAbsoluteX(){return absoluteLocal.getX();}
		public double getAbsoluteY(){return absoluteLocal.getY();}
		public double getAbsoluteZ(){return absoluteLocal.getZ();}
		public Ponto3D getAbsoluteLocation(){return absoluteLocal;}
		public void setAbsoluteLocation(Ponto3D absoluteLocal){this.absoluteLocal=absoluteLocal;}
		public void setAbsoluteLocation(double x,double y,double z){absoluteLocal.setLocation(x,y,z);}
	private double width;
		public double getWidth(){return width;}
		public void setWidth(double width){this.width=width;updateFilhos();}
	private Diretriz3D relativeDiretriz=new Diretriz3D();
		public double getRelativeAngleZ(){return relativeDiretriz.getAngleZ();}
		public double getRelativeAngleY(){return relativeDiretriz.getAngleY();}
		public double getRelativeAngleX(){return relativeDiretriz.getAngleX();}
		public Diretriz3D getRelativeOrientation(){return relativeDiretriz;}
		public void setRelativeOrientation(Diretriz3D relativeDiretriz){this.relativeDiretriz=relativeDiretriz;update();}
		public void setRelativeOrientation(double angleZ,double angleY,double angleX){relativeDiretriz.setOrientation(angleZ,angleY,angleX);update();}
	private double[][]orientation;
		public double[][]getTransformation(){return orientation;}
		public void setTransformation(double[][] orientation){this.orientation=orientation;}
	private Diretriz3D absoluteDiretriz=new Diretriz3D();
		public double getAbsoluteAngleZ(){return absoluteDiretriz.getAngleZ();}
		public double getAbsoluteAngleY(){return absoluteDiretriz.getAngleY();}
		public double getAbsoluteAngleX(){return absoluteDiretriz.getAngleX();}
		public Diretriz3D getAbsoluteOrientation(){return absoluteDiretriz;}
		public void setAbsoluteOrientation(Diretriz3D absoluteDiretriz){this.absoluteDiretriz=absoluteDiretriz;}
		public void setAbsoluteOrientation(double angleZ,double angleY,double angleX){absoluteDiretriz.setOrientation(angleZ,angleY,angleX);}
	private Osso pai=null;
		public Osso getPai(){return pai;}
		public void setPai(Osso pai){this.pai=pai;update();}
		public boolean hasPai(){return (pai!=null);}
	private List<Osso>filhos=new ArrayList<>();
		public List<Osso>getFilhos(){return filhos;}
		public void setFilhos(List<Osso>filhos){this.filhos=filhos;}
		public void addFilho(Osso filho){filhos.add(filho);}
		public void delFilho(Osso filho){filhos.remove(filho);}
	private Carne carne=new Carne(this);
		public Carne getCarne(){return carne;}
		public void setCarne(Carne carne){this.carne=carne;}
//	public Osso(Osso pai,double x,double y,int width,float angleR,float angleY,float angleX){	//NÃO USADO
//		setPai(pai);setLocation(x,y);setWidth(width);setPosition(angleR,angleY,angleX);
//	}
//	public Osso(){}																				//NÃO USADO
//	public Osso(Osso pai){setPai(pai);setLocation(0,0);setWidth(0);setPosition(0,0,0);}			//NÃO USADO
	public Osso(Esqueleto esqueleto,Osso pai,Element osso,HashMap<Integer,Forma>formasIDs,HashMap<Integer,Linha>linhasIDs,HashMap<Integer,Ponto>pontosIDs){
		this.esqueleto=esqueleto;
		setPai(pai);
		setNome(osso.getAttribute("nome"));													//INSERE NOME
		
//	    Node son=osso.getFirstChild();     
//		while(son.getNextSibling()!=null){
//			son=son.getNextSibling();
//			switch(son.getNodeName()){
//				case "carne":
//					final Element tagCarne=(Element)son;									//INSERE CARNE
//					setCarne(new Carne(this,tagCarne,formasIDs,linhasIDs,pontosIDs));
//				break;
//				case "osso":
//					final Element tagOsso=(Element)son;										//INSERE OSSO
//					if(tagOsso.getParentNode()!=osso)continue;
//					addFilho(new Osso(esqueleto,this,tagOsso,formasIDs,linhasIDs,pontosIDs));
//				break;
//			}
//		}
		
		final NodeList carnes=osso.getElementsByTagName("carne");							//INSERE CARNE
		for(int i=0,size=carnes.getLength();i<size;i++){
			final Element tagCarne=(Element)carnes.item(i);
			if(tagCarne.getParentNode()!=osso)continue;
			setCarne(new Carne(this,tagCarne,formasIDs,linhasIDs,pontosIDs));
		}
		
		final NodeList ossosSon=osso.getElementsByTagName("osso");							//INSERE OSSOS
		for(int i=0,size=ossosSon.getLength();i<size;i++){
			final Element tagOsso=(Element)ossosSon.item(i);
			if(tagOsso.getParentNode()!=osso)continue;
			addFilho(new Osso(esqueleto,this,tagOsso,formasIDs,linhasIDs,pontosIDs));
		}
		setWidth(Double.parseDouble(osso.getAttribute("width")));									//INSERE WIDTH
		getRelativeOrientation().setAngleZ(Double.parseDouble(osso.getAttribute("angleZ")));		//INSERE ANGLE-Z
		getRelativeOrientation().setAngleY(Double.parseDouble(osso.getAttribute("angleY")));		//INSERE ANGLE-Y
		getRelativeOrientation().setAngleX(Double.parseDouble(osso.getAttribute("angleX")));		//INSERE ANGLE-X
	}
	private void update(){
		if(hasPai()){
			setAbsoluteLocation(Ponto3D.getPonto3D(getPai().getTransformation()));
			setTransformation(getPontaMatrix(
					getPai().getTransformation(),
					new Diretriz3D(getPai().getRelativeAngleZ(),getRelativeAngleY(),getRelativeAngleX()),
					getWidth())
			);
			final Ponto3D pontoIni=getAbsoluteLocation();
			final Ponto3D pontoFim=Ponto3D.getPonto3D(getTransformation());

//			if(getNome()!=null&&getNome().equals("Braço Direito")){
//				System.out.println("F: "+pontoFim.getZ());
//				System.out.println("I: "+pontoIni.getZ());
//				BigDecimal fim=new BigDecimal(pontoFim.getZ());
//				BigDecimal ini=new BigDecimal(pontoIni.getZ());
//				BigDecimal rel=fim.subtract(ini);
////				System.out.println(rel);
//				System.out.println((float)(pontoFim.getZ()-pontoIni.getZ()));
//				
//				System.out.println("!: "+Math.asin((0.0000000000000000000000000026)/getWidth()));
//			}
//			final double comeco=Math.toRadians(0);
			final double reto=Math.toRadians(90);
			final double meiaVolta=Math.toRadians(180);
			
			//TODO: ARRUMAR O ÂNGULO DE AZ
			//TODO: ÂNGULOS R RESETAM APÓS 360
//			double absoluteAngleY=Math.asin((double)(pontoFim.getZ()-pontoIni.getZ())/getWidth());
			
//			Ponto pontoFimRelativeX=pontoFim.
			
			double absoluteAngleY=(double)(Math.atan2((double)(pontoFim.getY()-pontoIni.getY()),(double)(pontoFim.getZ()-pontoIni.getZ())));
			if(absoluteAngleY>reto&&absoluteAngleY<meiaVolta)absoluteAngleY=(absoluteAngleY*-1)+meiaVolta+meiaVolta+reto;
				else absoluteAngleY=(absoluteAngleY*-1)+reto;
			
			double absoluteAngleX=(double)(Math.atan2((double)(pontoFim.getY()-pontoIni.getY()),(double)(pontoFim.getX()-pontoIni.getX())));
//			absoluteAngleX+=(absoluteAngleX<-reto&&absoluteAngleX>-meiaVolta?meiaVolta+reto:-reto);	//ROTACIONA CÍRCULO DE DIR=0 PARA INF=0 (DIR É -, ESQ É +)
//			if(absoluteAngleX>reto&&absoluteAngleX<meiaVolta)absoluteAngleX=((absoluteAngleX-reto)*-1)+meiaVolta+meiaVolta;
//				else absoluteAngleX=(absoluteAngleX-reto)*-1;
			if(absoluteAngleX>reto&&absoluteAngleX<meiaVolta)absoluteAngleX=absoluteAngleX-reto;
				else absoluteAngleX=absoluteAngleX+meiaVolta+reto;
			
			setAbsoluteOrientation(
					(double)(getPai().getAbsoluteAngleZ()+getRelativeAngleZ()),
					absoluteAngleY,
					absoluteAngleX
			);
		}else{
			setAbsoluteLocation(getRelativeLocation());
			setTransformation(getPontaMatrix(
					getAbsoluteLocation().getPointMatrix(),
					new Diretriz3D(0,getRelativeAngleY(),getRelativeAngleX()),
					getWidth())
			);
			setAbsoluteOrientation(getRelativeAngleZ(),getRelativeAngleY(),getRelativeAngleX());
		}
		updateFilhos();
	}
	private void updateFilhos(){for(Osso osso:getFilhos())osso.update();}
	private double[][]getPontaMatrix(double[][]matrix,Diretriz3D diretriz,double distance){
		final double x=diretriz.getAngleX(),y=diretriz.getAngleY(),z=diretriz.getAngleZ();
//		final double d=(distance);		//A PARTE ROTACIONADA TEM WIDTH DE 1, CONSIDERADA NA TRANSLAÇÃO
		//RY(z), RZ(x), RX(y), E TY(d)
		return Ponto3D.transform(matrix,Ponto3D.transform(Diretriz3D.getRotationY(z),Diretriz3D.getRotationZ(x),Diretriz3D.getRotationX(y),Ponto3D.getTranslation(0,distance,0)));
	}
	public void draw(Graphics2D imagemEdit){
		carne.draw(imagemEdit);
		for(Osso osso:getFilhos())osso.draw(imagemEdit);
		drawOsso(imagemEdit);
	}
	private void drawOsso(Graphics2D imagemEdit){

		
		imagemEdit.setColor(Color.RED);
		
		final Ponto3D pontoIni=getAbsoluteLocation();
		final Ponto3D pontoFim=Ponto3D.getPonto3D(getTransformation());
		
		final double grossura=getWidth()/10;
		final double lateral=Math.sqrt(Math.pow((getWidth()/4)*3,2)+Math.pow(grossura,2));	//SQRT(((w/4)*3)²+g²) = TAMANHO DA LATERAL
		final double lateralAngle=Math.asin(grossura/lateral);								//SIN^-1(g/l) = ÂNGULO ENTRE LATERAL E MEIO DO OSSO
		final double reto=Math.toRadians(90);

		if(getNome().equals("Braço Direito")){
//			imagemEdit.drawString("AZ: "+getAbsoluteAngleZ(),10,30*1);
//			imagemEdit.drawString("AY: "+getAbsoluteAngleY(),10,30*2);
//			imagemEdit.drawString("AX: "+getAbsoluteAngleX(),10,30*3);
//			imagemEdit.drawString("RZ: "+getRelativeAngleZ(),10,30*4);
//			imagemEdit.drawString("RY: "+getRelativeAngleY(),10,30*5);
//			imagemEdit.drawString("RX: "+getRelativeAngleX(),10,30*6);
			final double[]vars=new double[]{getAbsoluteAngleZ(),getAbsoluteAngleY(),getAbsoluteAngleX(),getRelativeAngleZ(),getRelativeAngleY(),getRelativeAngleX()};
			for(int c=0;c<vars.length;c++)vars[c]=Math.toDegrees(vars[c]);
			imagemEdit.drawString("AZ: "+vars[0],10,30*1);
			imagemEdit.drawString("AY: "+vars[1],10,30*2);
			imagemEdit.drawString("AX: "+vars[2],10,30*3);
			imagemEdit.drawString("RZ: "+vars[3],10,30*4);
			imagemEdit.drawString("RY: "+vars[4],10,30*5);
			imagemEdit.drawString("RX: "+vars[5],10,30*6);
		}
		
		final double[][]matrix=getTransformation();
		final double meiaVolta=(double)(Math.PI+lateralAngle);
		final Ponto pontoBase1=Ponto3D.getPonto3D(getPontaMatrix(matrix,new Diretriz3D(getRelativeAngleZ(),
				meiaVolta,lateralAngle),lateral)).getPonto2D();
		final Ponto pontoBase2=Ponto3D.getPonto3D(getPontaMatrix(matrix,new Diretriz3D((double)(getRelativeAngleZ()+reto),
				meiaVolta,lateralAngle),lateral)).getPonto2D();
		final Ponto pontoBase3=Ponto3D.getPonto3D(getPontaMatrix(matrix,new Diretriz3D((double)(getRelativeAngleZ()+reto+reto),
				meiaVolta,lateralAngle),lateral)).getPonto2D();
		final Ponto pontoBase4=Ponto3D.getPonto3D(getPontaMatrix(matrix,new Diretriz3D((double)(getRelativeAngleZ()+reto+reto+reto),
				meiaVolta,lateralAngle),lateral)).getPonto2D();
		
		final GeneralPath forma=new GeneralPath();
		forma.setWindingRule(GeneralPath.WIND_NON_ZERO);
		forma.moveTo(pontoIni.getX(),pontoIni.getY());		//0
		forma.lineTo(pontoBase1.getX(),pontoBase1.getY());	//1
		forma.lineTo(pontoBase2.getX(),pontoBase2.getY());	//2
		forma.lineTo(pontoFim.getX(),pontoFim.getY());		//3
		forma.lineTo(pontoBase3.getX(),pontoBase3.getY());	//4
		forma.lineTo(pontoBase2.getX(),pontoBase2.getY());	//5
		forma.lineTo(pontoIni.getX(),pontoIni.getY());		//6
		forma.lineTo(pontoBase3.getX(),pontoBase3.getY());	//7
		forma.lineTo(pontoBase4.getX(),pontoBase4.getY());	//8
		forma.lineTo(pontoFim.getX(),pontoFim.getY());		//9
		forma.lineTo(pontoBase1.getX(),pontoBase1.getY());	//10
		forma.lineTo(pontoBase4.getX(),pontoBase4.getY());	//11
//		forma.lineTo(pontoIni.getX(),pontoIni.getY());		//12
		forma.closePath();
		imagemEdit.setColor(Color.BLUE);
		imagemEdit.draw(forma);
		imagemEdit.setColor(Color.RED);
		imagemEdit.drawLine((int)pontoIni.getX(),(int)pontoIni.getY(),(int)pontoFim.getX(),(int)pontoFim.getY());
//		pontoIni.getPonto2D().draw(imagemEdit,new Ponto(0,0),0);
//		pontoFim.getPonto2D().draw(imagemEdit,new Ponto(0,0),0);
		
		
		
		

//		final double[][]M=getAbsoluteOrientation();
//		new Linha(){{//ORELHA-DIR
//			add(setPonto(M,new Ponto3D(30,50,30),3));
//			add(setPonto(M,new Ponto3D(45,55,45),5));
//			add(setPonto(M,new Ponto3D(55,38,55),8));
//			add(setPonto(M,new Ponto3D(45,20,45),5));
//			add(setPonto(M,new Ponto3D(30,25,30),3));
//		}}.draw(imagemEdit,new Ponto(0,0),0);
//		new Linha(){{//ORELHA-ESQ
//			add(setPonto(M,new Ponto3D(-30,50,30),3));
//			add(setPonto(M,new Ponto3D(-45,55,45),5));
//			add(setPonto(M,new Ponto3D(-55,38,55),8));
//			add(setPonto(M,new Ponto3D(-45,20,45),5));
//			add(setPonto(M,new Ponto3D(-30,25,30),3));
//		}}.draw(imagemEdit,new Ponto(0,0),0);
//		new Linha(){{//OLHO-DIR
//			add(setPonto(M,new Ponto3D(16,		50,		0),5));
//			add(setPonto(M,new Ponto3D(26+3,	60-3,	10+3),5));
//			add(setPonto(M,new Ponto3D(31,		65,		15),5));
//			add(setPonto(M,new Ponto3D(26+3,	75+3,	10+3),5));
//			add(setPonto(M,new Ponto3D(16,		85,		0),5));
//			add(setPonto(M,new Ponto3D(6-3,		75+3,	-10-3),5));
//			add(setPonto(M,new Ponto3D(1,		65,		-15),5));
//			add(setPonto(M,new Ponto3D(6-3,		60-3,	-10-3),5));
//			add(this.get(0));
//			add(this.get(1));
//		}}.draw(imagemEdit,new Ponto(0,0),0);
	}
//	private Ponto setPonto(double[][]M,Ponto3D p,double size){
//		final Ponto ponto=Ponto3D.getPonto3D(Ponto3D.transform(
//				getPontaMatrix(M,new Diretriz3D(getRelativeAngleZ(),Math.PI,0),p.getY()),
//				Ponto3D.getTranslationX(p.getX()),
//				Ponto3D.getTranslationZ(p.getZ())
//		)).getPonto2D();
//		ponto.setSize(size);
//		return ponto;
//	}
}