package com.example.magic.resources;

import com.example.magic.entities.Card;
import com.example.magic.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cards")
public class CardController {
    @Autowired
    private CardRepository cardRepository;

    @GetMapping
    public ResponseEntity<List<Card>> findAll(@RequestParam(required = false) String value) {
        List<Card> cards = new ArrayList<>();

        if (value == null || !List.of("name", "price").contains(value.trim().toLowerCase())) {
            return ResponseEntity.ok(cardRepository.findAll());
        }

        if ("name".equalsIgnoreCase(value.trim())) {
            cards = cardRepository.findAllByOrderByName();
        } else if ("price".equalsIgnoreCase(value.trim())) {
            cards = cardRepository.findAllByOrderByPrice();
        }

        return ResponseEntity.ok(cards);
    }

    @GetMapping(path = "{player}")
    public ResponseEntity<List<Card>> findAllByPlayer(@RequestParam(required = false) String value, @PathVariable String player) {
        List<Card> cards = new ArrayList<>();

        if (value == null || !List.of("name", "price").contains(value.trim().toLowerCase())) {
            return ResponseEntity.ok(cardRepository.findAllByPlayer(player));
        }

        if ("name".equalsIgnoreCase(value.trim())) {
            cards = cardRepository.findAllByPlayerOrderByName(player);
        } else if ("price".equalsIgnoreCase(value.trim())) {
            cards = cardRepository.findAllByPlayerOrderByPrice(player);
        }

        return ResponseEntity.ok(cards);
    }

    @PostMapping
    public ResponseEntity<Card> save(@RequestBody Card card) {
        Card savedCard = cardRepository.save(card);
        return ResponseEntity.accepted().body(savedCard);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            cardRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            return new ResponseEntity("A carta não foi encontrada e por isso nao pode ser excluída", HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Card> update(@PathVariable Long id, @RequestBody Card card) {
        Optional<Card> cardData = cardRepository.findById(id);

        if (cardData.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Card newCard = cardData.get();
        newCard.setPrice(card.getPrice());
        newCard.setQuantity(card.getQuantity());

        return ResponseEntity.accepted().body(cardRepository.save(newCard));
    }
}
