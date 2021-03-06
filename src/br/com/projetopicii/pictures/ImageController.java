package br.com.projetopicii.pictures;

import javax.swing.ImageIcon;

public class ImageController extends ImageIcon {
	private static final long serialVersionUID = -20661163161080230L;

	public static final ImageController informacao1 = ImageLoad("tutorial/Informa��o1.png");

	public static final ImageController informacao2 = ImageLoad("tutorial/Informa��o2.png");

	public static final ImageController informacao3 = ImageLoad("tutorial/Informa��o3.png");
	
	public static final ImageController balaoResultado = ImageLoad("tutorial/Bal�oResultado.png");
	
	public static final ImageController FundoBiblioteca = ImageLoad("biblioteca/FundoBiblioteca.png");

	public static final ImageController TerminalPesquisa = ImageLoad("biblioteca/TerminalPesquisa.png");
	
	public static final ImageController findMyBook_1366x768 = ImageLoad("biblioteca/FindMyBook_1366x768.png");
	
	public static final ImageController findMyBook_1920x1080 = ImageLoad("biblioteca/FindMyBook_1920x1080.png");

	private ImageController() {

	}

	private ImageController(String as_filename) {
		super(ImageController.class.getResource(as_filename));
	}

	private static final ImageController ImageLoad(String as_filename) {

		return (new ImageController(as_filename));
	}

}
