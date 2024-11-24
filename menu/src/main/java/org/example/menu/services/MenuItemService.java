package org.example.menu.services;

import org.example.menu.entities.MenuItem;
import org.example.menu.entities.MenuItemDTO;
import org.example.menu.entities.RestaurantDTO;
import org.example.menu.repositories.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<MenuItemDTO> getAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MenuItemDTO> getMenuItemsByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<MenuItemDTO> getMenuItemById(Long id) {
        return menuItemRepository.findById(id).map(this::convertToDTO);
    }

    private MenuItemDTO convertToDTO(MenuItem menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());

        // Fetch restaurant details
        RestaurantDTO restaurantDTO = restTemplate.getForObject(
                "http://localhost:8081/api/restaurants/" + menuItem.getRestaurantId(),
                RestaurantDTO.class
        );
        dto.setRestaurant(restaurantDTO);

        return dto;
    }
}
