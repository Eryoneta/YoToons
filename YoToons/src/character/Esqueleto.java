package character;
import java.awt.Graphics2D;
import java.util.HashMap;

import org.w3c.dom.Element;
public class Esqueleto{
	private Osso ossoBase;
		public Osso getOssoBase(){return ossoBase;}
		public void setOssoBase(Osso ossoBase){this.ossoBase=ossoBase;}
		public Ponto3D getLocation(){return getOssoBase().getRelativeLocation();}
		public void setLocation(Ponto3D local){getOssoBase().setRelativeLocation(local);}
		public void setLocation(double x,double y,double z){getOssoBase().setRelativeLocation(x,y,z);}
	private boolean show;
		public boolean getShow(){return show;}
		public void setShow(boolean show){this.show=show;}
	public Esqueleto(Element esqueleto,HashMap<Integer,Forma>formasIDs,HashMap<Integer,Linha>linhasIDs,HashMap<Integer,Ponto>pontosIDs){
		final Element tagOssoBase=(Element)esqueleto.getElementsByTagName("osso").item(0);								//INSERE OSSO-BASE
		setOssoBase(new Osso(this,null,tagOssoBase,formasIDs,linhasIDs,pontosIDs));
		//INSERE X, Y E Z
		setLocation(Double.parseDouble(esqueleto.getAttribute("x")),Double.parseDouble(esqueleto.getAttribute("y")),Double.parseDouble(esqueleto.getAttribute("z")));
	}
	public void draw(Graphics2D imagemEdit){getOssoBase().draw(imagemEdit);}
//	public void update(Graphics2D imagemEdit){getOssoBase().update();}
}