package instagram;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

abstract class InstagramObject {
	
	private WebElement element;
	
	public InstagramObject(WebElement element) {
		if (!this.checkClass(element.getAttribute("class"))) {
			throw new IllegalArgumentException("Class attribute of element doesn't match the defined CSS Class for this object.");
		}
		this.setElement(element);
		this.setup();
	}
	
	/**
	 * Checks a string that represents a WebElement's CSS class attribute. Should return true if the element's class matches that of any element that this object can parse.
	 * @param cssClass the class of the object being checked
	 * @return true if the class matches that of an element type that can be parsed by this object
	 */
	public abstract boolean checkClass(String cssClass);
	
	/**
	 * Parses the WebElement held in this object after instantiation and instantiates all fields of this object.
	 * It is called in the InstagramObject constructor, and shouldn't be called anywhere else.
	 */
	abstract void setup();
	
	/**
	 * If the class requirement is the same, then check if the elements are equal
	 */
	public boolean equals(Object o) {
		if (o instanceof InstagramObject) {
			if (((InstagramObject) o).checkClass(element.getAttribute("class")) ) {
				if (((InstagramObject) o).getElement().equals(element))
					return true;
			}
		} return false;
	}
	
	/**
	 * Is the element represented by this object visible or not?
	 * @return whether or not this object's element is visible
	 */
	public boolean isVisible() {
		return element.isDisplayed();
	}
	
	final WebElement findElement(By arg0) {
		return element.findElement(arg0);
	}

	final List<WebElement> findElements(By arg0) {
		return element.findElements(arg0);
	}
	
	final String getTextByXpath(String xpath) {
		return findElement(By.xpath(xpath)).getText();
	}
	
	public WebElement getElement() {
		return element;
	}

	public void setElement(WebElement element) {
		this.element = element;
	}
	
}
