package net.wisedream.tasklet.manager;

import junit.framework.TestCase;

public class StackTest extends TestCase{
	public void testStack(){
		StackManager.Stack<String> stack = new StackManager.Stack<String>();
		stack.push("1");
		stack.push("2");
		System.out.println(stack);
		stack.push("3");
		assertEquals("3", stack.peek());
		System.out.println(stack);
		assertEquals("3", stack.pop());
		System.out.println(stack);
	}

}
