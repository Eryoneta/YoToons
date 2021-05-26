package character;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashMap;

import org.w3c.dom.Element;
public class Ponto{
	private Ponto pontoFinal;
		public Ponto getPontoFinal(){return pontoFinal;}
		public void setPontoFinal(Ponto pontoFinal){this.pontoFinal=pontoFinal;}
	private double x=0;
		public double getX(){return x;}
		public void setX(double x){this.x=x;}
	private double y=0;
		public double getY(){return y;}
		public void setY(double y){this.y=y;}
	public void setLocation(double x,double y){setX(x);setY(y);}
	public void setLocation(Ponto ponto){setX(ponto.x);setY(ponto.y);}
	private double size=1;
		public double getSize(){return size;}
		public void setSize(double size){this.size=size;}
	public Ponto(){}
	public Ponto(double x,double y){setLocation(x,y);}
	public Ponto(Ponto ponto){
		setLocation(ponto.getX(),ponto.getY());
		setSize(ponto.getSize());
	}
	public Ponto(Element ponto,HashMap<Integer,Ponto>listaIDs){
		final int id=Integer.parseInt(ponto.getAttribute("id"));
		if(!listaIDs.containsKey(id))listaIDs.put(id,new Ponto());
		pontoFinal=listaIDs.get(id);
		setX(Double.parseDouble(ponto.getAttribute("x")));
		setY(Double.parseDouble(ponto.getAttribute("y")));
		setSize(Double.parseDouble(ponto.getAttribute("size")));
	}
	
	public void set(double x,double y){setX(x);setY(y);}
	public void set(Ponto ponto){setX(ponto.getX());setY(ponto.getY());}
	public Ponto add(Ponto ponto){return new Ponto((double)(this.getX()+ponto.getX()),(double)(this.getY()+ponto.getY()));}
	public Ponto sub(Ponto ponto){return new Ponto((double)(this.getX()-ponto.getX()),(double)(this.getY()-ponto.getY()));}
	public Ponto scale(double escala){return new Ponto(this.getX()*escala,this.getY()*escala);}
	public Ellipse2D.Double getForm(Ponto local,double angleX){
		final Ponto newPonto=getRelativeLocal(local,angleX);
		
		final double raio=getSize()/2;
		return new Ellipse2D.Double(newPonto.getX()-raio,newPonto.getY()-raio,getSize(),getSize());
		
//		final Path2D.Double forma=new Path2D.Double();
//		forma.moveTo(getX()+size,getY());
//		//TODO: REFAZER COM RADIANS!
//		for(int angle=0;angle<=360;angle+=45){
//			Ponto ponto=getRelativePoint(angle,size);
//			forma.lineTo(ponto.getX(),ponto.getY());
//		}
//		return forma;
	}
	public Ponto getRelativeLocal(Ponto local,double angleX){
		return Ponto3D.getPonto3D(Ponto3D.transform(
				Ponto3D.getTranslation(local.getX(),local.getY(),0),
				Diretriz3D.getRotationZ(angleX),
				new Ponto3D(getX(),getY(),0).getPointMatrix()
		)).getPonto2D();
	}
	
	
	
//	public Path2D.Double getRelativeForm(Ponto local){
//		final Path2D.Double forma=getForm();
//		forma.transform(AffineTransform.getTranslateInstance(local.getX(),local.getY()));
//		return forma;
//	}
	
	//TODO: REFAZER, MAS ORIGINAL
	public double getDistance(Ponto ponto){return new Point2D.Double(this.getX(),this.getY()).distance(new Point2D.Double(ponto.getX(),ponto.getY()));}
	//TODO: REFAZER COM RADIANS!
	public Ponto getRelativePoint(double angle,double distance){			//RETORNA PONTO COM DISTÂNCIA E ÂNGULO RELATIVO A ESTE
		angle-=90;		//CORREÇÃO DE 0 NO TOPO PARA NA DIREITA
		return new Ponto(getX()+Math.cos(Math.toRadians(angle))*distance,getY()+Math.sin(Math.toRadians(angle))*distance);
	}
	//TODO: REFAZER COM RADIANS!
	public double getAngle(Ponto ponto){			//DEGREES(0-360), COMEÇA NA ESQUERDA E SEGUE SENTIDO HORÁRIO, O CENTRO SENDO ESTE PONTO
		return ((Math.atan2(getY()-ponto.getY(),getX()-ponto.getX())*180/Math.PI)%360)-90;
	}
	public void draw(Graphics2D imagemEdit,Ponto local,double angleX){
		final Color oldCor=imagemEdit.getColor();
		final Stroke oldLinha=imagemEdit.getStroke();
		imagemEdit.setColor(Color.RED);
		imagemEdit.setStroke(new BasicStroke(2f));
		imagemEdit.draw(getForm(local,angleX));
		imagemEdit.setColor(oldCor);
		imagemEdit.setStroke(oldLinha);
	}
}