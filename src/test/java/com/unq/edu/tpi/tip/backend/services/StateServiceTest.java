package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.exceptions.StateNotFoundException;
import com.unq.edu.tpi.tip.backend.models.State;
import com.unq.edu.tpi.tip.backend.repositories.StateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StateServiceTest{

	@InjectMocks
	private StateService stateService;

	@Mock
	private StateRepository stateRepository;

	State stateMock;

	@Before

	public void setUp(){
		stateMock = mock(State.class);
	}

	@Test
	public void whenISaveAnStateMustIterateOneTimeWithTheRepository(){
		stateService.save(stateMock);
		verify(stateRepository, times(1)).save(stateMock);
	}

	@Test(expected = StateNotFoundException.class)
	public void whenITryToFindByIdWhenDoesNotExistMustRaiseAnStateNotFoundException() throws StateNotFoundException
	{
		stateService.findById(1L);
	}

	@Test
	public void whenIFindByIdWhenExistMustIterateOneTimeWithTheRepository() throws StateNotFoundException
	{
		Optional<State> state = Optional.of(stateMock);
		when( stateRepository.findById(anyLong())).thenReturn(state);
		stateService.findById(1L);

		verify(stateRepository, times(1)).findById(anyLong());
	}
}
