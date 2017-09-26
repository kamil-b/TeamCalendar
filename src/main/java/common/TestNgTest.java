package common;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNgTest {

	Dog doggy = new Dog();
	String name = null;
	

	@BeforeTest
	public void initialisation(){
		doggy = Mockito.mock(Dog.class);
		System.out.println("before test");
	}
	
	@BeforeMethod
	public void initialisation2(){
		System.out.println("before method");
	}
	
	@BeforeSuite
	public void initialisation3(){
		System.out.println("before suite");
	}
	@Test
	public void checkifNotNull() {
		Assert.assertTrue(doggy != null);
	}
	
	@Test
	public void checkifNotCat() {
		Assert.assertFalse(doggy instanceof Cat);
	}
	
	@Test
	public void makeVoice() {
		//Mockito.when(doggy.getVoice()).thenCallRealMethod();
		Mockito.when(doggy.getVoice()).thenReturn( "woow wwow");
		Assert.assertEquals(doggy.getVoice(), "woow wwow");
	}
	
	public void somethng(){
		System.out.println("something");
	}
}

class Dog {
	
	String getVoice(){
		return "woow wwow";
	}
	
	String name = "killer";
	int age = 4;
}

class Cat extends Dog{
	String name = "prunny";
	int age = 2;
}