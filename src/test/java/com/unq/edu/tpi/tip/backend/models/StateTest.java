package com.unq.edu.tpi.tip.backend.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StateTest{

	@Test
	public void generateAvailableStateMustHaveEmtpyStringState(){
		State aState = new AvailableState();
		assertEquals(aState.getState(), "empty");
	}

	@Test
	public void generateUsedStateMustHaveInUseStringState(){
		State aState = new UsedState();
		assertEquals(aState.getState(), "inUse");
	}

	@Test
	public void generateStateHaveNullsOrderTable(){
		State aState = new UsedState();
		aState.setId(1L);
		assertEquals(aState.getOrderTable(), null);
		assertEquals(aState.getId(), 1);
	}

}
