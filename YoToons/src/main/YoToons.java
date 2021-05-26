package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import character.Osso;
import character.Personagem;
@SuppressWarnings("serial")
public class YoToons{
	public static void main(String[]args){new YoToons();}
	private YoToons(){
		inicio();
		new Timer().scheduleAtFixedRate(new TimerTask(){
			public void run(){
				desenhar();
			}
		},100,1000/30);
	}
	private Image frame=new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
	private JFrame janela=new JFrame(){{
		setTitle("YoToons");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(618,618);
        setBackground(Color.BLACK);
		setLocationRelativeTo(null);
		add(new JPanel(){protected void paintComponent(Graphics quadro){
			try{
				quadro.drawImage(frame,0,0,janela.getWidth(),janela.getHeight(),null);
			}catch(Exception erro){}
		}});
		setVisible(true);
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent k){
				if(k.getKeyCode()==KeyEvent.VK_ENTER)animate();
			}
		});
		addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent m){
			}
			public void mousePressed(MouseEvent m){
				mousePressed=m.getPoint();
				
			}
		});
		addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent m){
				final Point diff=new Point(mousePressed.x-m.getX(),mousePressed.y-m.getY());
				final double mouse=Math.toRadians(diff.y);
				final Osso cabeca=personagem.getEsqueleto().getOssoBase().getFilhos().get(1).getFilhos().get(0);
				final Osso antebraco=personagem.getEsqueleto().getOssoBase().getFilhos().get(0).getFilhos().get(0);
				final Osso braco=personagem.getEsqueleto().getOssoBase().getFilhos().get(0).getFilhos().get(0).getFilhos().get(0);
				switch(m.getModifiersEx()){
					case MouseEvent.BUTTON1_DOWN_MASK+MouseEvent.CTRL_DOWN_MASK:
						cabeca.setRelativeOrientation(cabeca.getRelativeAngleZ(),cabeca.getRelativeAngleY(),cabeca.getRelativeAngleX()+mouse);//X
					break;
					case MouseEvent.BUTTON2_DOWN_MASK+MouseEvent.CTRL_DOWN_MASK:
						cabeca.setRelativeOrientation(cabeca.getRelativeAngleZ()+mouse,cabeca.getRelativeAngleY(),cabeca.getRelativeAngleX());//Z
					break;
					case MouseEvent.BUTTON3_DOWN_MASK+MouseEvent.CTRL_DOWN_MASK:
						cabeca.setRelativeOrientation(cabeca.getRelativeAngleZ(),cabeca.getRelativeAngleY()+mouse,cabeca.getRelativeAngleX());//Y
					break;
					case MouseEvent.BUTTON1_DOWN_MASK+MouseEvent.SHIFT_DOWN_MASK:
						antebraco.setRelativeOrientation(antebraco.getRelativeAngleZ(),antebraco.getRelativeAngleY(),antebraco.getRelativeAngleX()+mouse);//X
					break;
					case MouseEvent.BUTTON2_DOWN_MASK+MouseEvent.SHIFT_DOWN_MASK:
						antebraco.setRelativeOrientation(antebraco.getRelativeAngleZ()+mouse,antebraco.getRelativeAngleY(),antebraco.getRelativeAngleX());//Z
					break;
					case MouseEvent.BUTTON3_DOWN_MASK+MouseEvent.SHIFT_DOWN_MASK:
						antebraco.setRelativeOrientation(antebraco.getRelativeAngleZ(),antebraco.getRelativeAngleY()+mouse,antebraco.getRelativeAngleX());//Y
					break;
					case MouseEvent.BUTTON1_DOWN_MASK:
						braco.setRelativeOrientation(braco.getRelativeAngleZ(),braco.getRelativeAngleY(),braco.getRelativeAngleX()+mouse);//X
					break;
					case MouseEvent.BUTTON2_DOWN_MASK:
						braco.setRelativeOrientation(braco.getRelativeAngleZ()+mouse,braco.getRelativeAngleY(),braco.getRelativeAngleX());//Z
					break;
					case MouseEvent.BUTTON3_DOWN_MASK:
						braco.setRelativeOrientation(braco.getRelativeAngleZ(),braco.getRelativeAngleY()+mouse,braco.getRelativeAngleX());//Y
					break;
				}
//				ossoT1.setRelativeRotation(osso1.getAbsoluteRotation());
//				ossoTB.setRelativeRotation(ossoBase.getAbsoluteRotation());
//				System.out.print("X: "+Math.toDegrees(ossoTB.getAbsoluteAngleX())+" | ");
//				System.out.println(Math.toDegrees(ossoT1.getAbsoluteAngleX()));
//				System.out.print("Y: "+Math.toDegrees(ossoTB.getAbsoluteAngleY())+" | ");
//				System.out.println(Math.toDegrees(ossoT1.getAbsoluteAngleY()));
				mousePressed=m.getPoint();
			}
		});
	}};
	private Point mousePressed;
	private void desenhar(){
		BufferedImage imagem=new BufferedImage(janela.getWidth(),janela.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D imagemEdit=(Graphics2D)imagem.getGraphics();
		imagemEdit.setColor(new Color(230,230,230));
		imagemEdit.fillRect(0,0,janela.getWidth(),janela.getHeight());
		
		imagemEdit.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		personagem.draw(imagemEdit);
//		ossoTB.draw(imagemEdit);
//		ossoT1.draw(imagemEdit);
		
		frame=imagem;
		imagemEdit.dispose();
		janela.repaint();
//		System.exit(0);
	}
//	private Osso ossoTB=new Osso(){{
//		setWidth(100);
//		setRelativeLocation(50,100,0);
//	}};
//	private Osso ossoT1=new Osso(){{
//		setWidth(100);
//		setRelativeLocation(50,200,0);
//	}};
	private void inicio(){
		abrir("Manequim.char");
		
//		try{Thread.sleep(500);}catch(InterruptedException erro){}
//		animate();
		
	}
	private void animate(){
		new Thread(new Runnable(){
			public void run(){
				final Osso ossoBase=personagem.getEsqueleto().getOssoBase();
				final Osso osso1=personagem.getEsqueleto().getOssoBase().getFilhos().get(0);
				osso1.setRelativeOrientation(0,0,0);
				ossoBase.setRelativeOrientation(0,0,0);
				ossoBase.setWidth(ossoBase.getWidth());
//				final double reto=Math.toRadians(90);
//				final double semireto=Math.toRadians(45);
				try{
					while(true){
						ossoBase.setRelativeOrientation(ossoBase.getRelativeAngleZ()+.01,ossoBase.getRelativeAngleY(),ossoBase.getRelativeAngleX());
						Thread.sleep(5);
					}
//					while(ossoBase.getRelativeAngleZ()<semireto){
//						ossoBase.setRelativeRotation(ossoBase.getRelativeAngleZ()+.01,ossoBase.getRelativeAngleY(),ossoBase.getRelativeAngleX());
//						Thread.sleep(10);
//					}
//					while(osso1.getRelativeAngleY()<reto){
//						osso1.setRelativeRotation(osso1.getRelativeAngleZ(),osso1.getRelativeAngleY()+.01,osso1.getRelativeAngleX());
//						Thread.sleep(10);
//					}
//					while(ossoBase.getRelativeAngleY()<semireto){
//						ossoBase.setRelativeRotation(ossoBase.getRelativeAngleZ(),ossoBase.getRelativeAngleY()+.01,ossoBase.getRelativeAngleX());
//						Thread.sleep(10);
//					}
//					while(ossoBase.getRelativeAngleX()<semireto){
//						ossoBase.setRelativeRotation(ossoBase.getRelativeAngleZ(),ossoBase.getRelativeAngleY(),ossoBase.getRelativeAngleX()+.01);
//						Thread.sleep(10);
//					}
				
				}catch(InterruptedException erro){}
			}
		}).start();
	}
//	private void novo(){
//		
//	}
	private Personagem personagem;
	private void abrir(String link){
		try{
			final Document personagemTags=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(link));
			final Element tagPersonagem=personagemTags.getDocumentElement();
			personagem=new Personagem(tagPersonagem);
		}catch(IOException|SAXException|ParserConfigurationException erro){mensagem("Erro ao abrir personagem!\n"+erro,ERRO);
			erro.printStackTrace();
		}
	}
//	private void salvar(){
//		
//	}
//	private void fechar(){
//		
//	}
	public final static int ERRO=0,AVISO=1;
	public static void mensagem(String mensagem,int tipo){
		switch(tipo){
			case AVISO:	JOptionPane.showMessageDialog(null,mensagem,"Aviso!",JOptionPane.WARNING_MESSAGE);break;
			case ERRO:	JOptionPane.showMessageDialog(null,mensagem,"Erro...!",JOptionPane.ERROR_MESSAGE);break;
		}
	}
}