package bot;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Bot properties class. If constructed with a string, it automatically loads from file.
 * @author aliu
 *
 */
class BotProperties extends java.util.Properties {

	private static final long serialVersionUID = 1L;
	
	private String document;
	private boolean hasFile;
	
	public BotProperties() {
		hasFile = false;
		document = null;
	}
	
	public BotProperties(String document) {
		this.setDocument(document);
		try {
			this.loadFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadFile() throws IOException {
		if (hasFile)
			this.loadFile(document);
		else throw new BotConfigurationException("Don't have a properly formatted filename to load from!");
	}
	
	public void storeFile() throws IOException {
		if (hasFile)
			this.storeFile(document);
		else throw new BotConfigurationException("Don't have a properly formatted filename to store to!");
	}
	
	/**
	 * Wrapper to make it easier to load from file
	 * @param docName
	 * @throws IOException
	 */
	private void loadFile(String docName) throws IOException {
		FileReader file = new FileReader(docName);
		this.load(file);
		file.close();
	}
	
	/**
	 * Wrapper to make it easier to store to file
	 * @param docName
	 * @throws IOException
	 */
	void storeFile(String docName) throws IOException {
		FileWriter file = new FileWriter(docName);
		this.store(file, "Bot Properties");
		file.close();
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document.trim();
		if (document != null && !document.trim().equals(""))
			hasFile = true;
	}

}
