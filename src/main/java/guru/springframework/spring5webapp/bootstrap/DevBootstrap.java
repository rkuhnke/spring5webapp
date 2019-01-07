package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.model.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by jt on 5/16/17.
 */
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public DevBootstrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData(){


        //Eric
        Publisher harperCollins = new Publisher("Harper Collins");
        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "1234");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);
        harperCollins.setBook(ddd);

        authorRepository.save(eric);
        bookRepository.save(ddd);
        publisherRepository.save(harperCollins);

        Optional<Book> byId = bookRepository.findById(ddd.getId());

        if (byId.isPresent()) {
            Book book = byId.get();
            book.setPublisher(harperCollins);
            bookRepository.save(book);
        }



        //Rod
        Publisher wrox = new Publisher("Wrox");
        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "23444");
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);
        wrox.setBook(noEJB);

        authorRepository.save(rod);
        bookRepository.save(noEJB);
        publisherRepository.save(wrox);

        Optional<Book> byId2 = bookRepository.findById(noEJB.getId());
        if (byId2.isPresent()) {
            Book book = byId2.get();
            book.setPublisher(wrox);
            bookRepository.save(book);
        }
    }
}
