package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.mappers.BeerMapper;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.web.controller.NotFoundException;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        Page<Beer> beerPage = getBeerPage(beerName, beerStyle, pageRequest);

        List<BeerDto> beerDtos = beerPage.getContent().stream()
                .map(i -> showInventoryOnHand ? beerMapper.beerToBeerDtoWithInventory(i) : beerMapper.beerToBeerDto(i))
                .collect(Collectors.toList());

        return new BeerPagedList(beerDtos, getPageRequest(beerPage.getPageable()), beerPage.getTotalElements());
    }

    @Override
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        return showInventoryOnHand ? beerMapper.beerToBeerDtoWithInventory(beer) : beerMapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        Beer beer = beerMapper.beerDtoToBeer(beerDto);
        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());


        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    private PageRequest getPageRequest(Pageable pageable){
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
    }

    private Page<Beer> getBeerPage(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {
        boolean emptyBeerName = StringUtils.isEmpty(beerName);
        boolean emptyBeerStyle = StringUtils.isEmpty(beerStyle);

        if (!emptyBeerName && !emptyBeerStyle) {
            return beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!emptyBeerName) {
            return  beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (!emptyBeerStyle) {
            return  beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        }

        return  beerRepository.findAll(pageRequest);
    }

}
