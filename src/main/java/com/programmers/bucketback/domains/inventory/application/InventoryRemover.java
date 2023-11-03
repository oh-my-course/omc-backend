package com.programmers.bucketback.domains.inventory.application;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.bucketback.domains.inventory.domain.InventoryItem;
import com.programmers.bucketback.domains.inventory.repository.InventoryItemRepository;
import com.programmers.bucketback.domains.inventory.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryRemover {

	private final InventoryRepository inventoryRepository;
	private final InventoryItemRepository inventoryItemRepository;
	private final InventoryReader inventoryReader;


	/** 인벤토리 삭제 */
	@Transactional
	public void remove(final Long inventoryId){

	}

	/** 인벤토리 아이템 삭제
	 * refactor : orphanRemoval = true 설정에 대해서 알아보고 리팩토링 잡아보기
	 * */
	@Transactional
	public void removeInventoryItems(final Long inventoryId){
		List<InventoryItem> inventoryItems = inventoryReader.inventoryItemRead(inventoryId);

		inventoryItems.forEach(inventoryItem -> inventoryItemRepository.delete(inventoryItem));
	}
}
