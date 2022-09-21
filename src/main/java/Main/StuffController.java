package Main;

import Main.model.StuffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Main.model.Stuff;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StuffController {

    @Autowired
    private StuffRepository stuffRepository;


    @GetMapping("/stuff")
    public List<Stuff> stuff() {
        Iterable<Stuff> stuffIterable = stuffRepository.findAll();
        ArrayList<Stuff> stuffArrayList = new ArrayList<>();
        for (Stuff stuff : stuffIterable) {
            stuffArrayList.add(stuff);
        }
        return stuffArrayList;
    }

    @PostMapping("/stuff")
    public int add(Stuff stuff) {

//        Iterable<Stuff> stuffIterable = stuffRepository.findAll();
//        ArrayList<Integer> stuffsId = new ArrayList<>();
//        for(Stuff stuff2 : stuffIterable){
//            stuffsId.add(stuff2.getId());
//        }
//        if(stuffsId.isEmpty()){
//            stuff.setId(1);
//        }else {
//            stuff.setId(stuffsId.size()+1);
//        }


        Stuff stuff1 = stuffRepository.save(stuff);

        return stuff1.getId();
    }

    @GetMapping("/stuff/{stuffId}")
    public ResponseEntity get(@PathVariable int stuffId) {
        Optional<Stuff> stuffOptional = stuffRepository.findById(stuffId);
        if (!stuffOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого предмета нет(");
        }
        return new ResponseEntity(stuffOptional.get(), HttpStatus.OK);
    }


    @PutMapping("/stuff/{stuffId}")
    public ResponseEntity refactor(@PathVariable int stuffId, Stuff stuff1) {

        Optional<Stuff> stuffOptional = stuffRepository.findById(stuffId);
        if (stuffOptional.isPresent()) {
            stuffOptional.get().setName(stuff1.getName());
            stuffOptional.get().setType(stuff1.getType());
        }

        if (!stuffOptional.isPresent() || !stuffOptional.get().getName().equals(stuff1.getName())) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Что-то пошло не так...");
        }

        stuffRepository.save(stuffOptional.get());


        return new ResponseEntity(stuffOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/stuff/{stuffId}")
    public ResponseEntity delete(@PathVariable int stuffId) {
        if (!stuffRepository.findById(stuffId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Что-то пошло не так...");
        }
        stuffRepository.deleteById(stuffId);

        return new ResponseEntity(HttpStatus.OK);

    }

    @DeleteMapping("/stuff")
    public ResponseEntity deleteAll() {
        stuffRepository.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }


}
