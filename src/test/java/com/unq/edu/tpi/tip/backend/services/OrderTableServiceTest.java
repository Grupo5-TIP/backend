package com.unq.edu.tpi.tip.backend.services;

import com.unq.edu.tpi.tip.backend.mappers.OrderTableMapper;
import com.unq.edu.tpi.tip.backend.models.OrderTable;
import com.unq.edu.tpi.tip.backend.repositories.OrderTableRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class OrderTableServiceTest {
	@InjectMocks
	private OrderTableService orderTableService;

	@Mock
	private OrderTableRepository orderTableRepository;
	@Mock
	private OrderTableMapper orderTableMapper;

	@Test
	public void whenIGetAllProductsMustInteractOneTimeWithMapperAndRepository(){
		orderTableService.getAll();
		verify(orderTableMapper, times(1)).mapEntitiesIntoDTOs(anyList());
		verify(orderTableRepository,times(1)).findAll();
	}

	@Test
	public void whenICreateAProductMustHasTheSameParamValuesAndMustInteractOneTimeWithMapperAndRepository(){
		OrderTable orderTable = mock(OrderTable.class);

		orderTableService.save(orderTable);

		verify(orderTableRepository,times(1)).save(any(OrderTable.class));

	}
}
