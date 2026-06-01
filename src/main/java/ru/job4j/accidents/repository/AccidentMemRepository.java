//package ru.job4j.accidents.repository;
//
//import org.springframework.stereotype.Repository;
//import ru.job4j.accidents.model.Accident;
//import ru.job4j.accidents.model.AccidentType;
//import ru.job4j.accidents.model.Rule;
//
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//
//@Repository
//public class AccidentMemRepository implements AccidentRepository {
//
//    private final AtomicInteger counter = new AtomicInteger(0);
//
//    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
//
//    @Override
//    public List<Accident> getAll() {
//        return new ArrayList<>(accidents.values());
//    }
//
//    @Override
//    public Optional<Accident> findById(Integer id) {
//        return Optional.ofNullable(accidents.get(id));
//    }
//
//    @Override
//    public Accident save(Accident accident, List<Integer> ruleIds) {
//        int id = counter.incrementAndGet();
//        accident.setId(id);
//
//        var typeOptional = findAccidentTypeById(accident.getType().getId());
//        typeOptional.ifPresent(accident::setType);
//
//        Set<Rule> rules = findRulesByIds(ruleIds);
//        accident.setRules(rules);
//
//        accidents.put(id, accident);
//        return accident;
//    }
//
//    @Override
//    public boolean update(Accident accident, List<Integer> ruleIds) {
//        var typeOptional = findAccidentTypeById(accident.getType().getId());
//        typeOptional.ifPresent(accident::setType);
//        Set<Rule> rules = findRulesByIds(ruleIds);
//        accident.setRules(rules);
//        return accidents.put(accident.getId(), accident) != null;
//    }
//
//    @Override
//    public boolean delete(Integer id) {
//        return accidents.remove(id) != null;
//    }
//
//    public List<AccidentType> getAccidentTypes() {
//        return List.of(
//                new AccidentType(1, "Две машины"),
//                new AccidentType(2, "Машина и человек"),
//                new AccidentType(3, "Машина и велосипед")
//        );
//    }
//
//    public Optional<AccidentType> findAccidentTypeById(Integer id) {
//        return getAccidentTypes().stream()
//                .filter(a -> a.getId().equals(id))
//                .findFirst();
//    }
//
//    public List<Rule> getRules() {
//        return List.of(
//                new Rule(1, "Статья 1"),
//                new Rule(2, "Статья 2"),
//                new Rule(3, "Статья 3")
//        );
//    }
//
//    public Set<Rule> findRulesByIds(List<Integer> ids) {
//        if (ids == null || ids.isEmpty()) {
//            return Set.of();
//        }
//        return getRules().stream()
//                .filter(rule -> ids.contains(rule.getId()))
//                .collect(Collectors.toSet());
//    }
//}
