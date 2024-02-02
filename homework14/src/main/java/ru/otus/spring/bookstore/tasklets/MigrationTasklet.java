//package ru.otus.spring.bookstore.tasklets;
//
//
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.stereotype.Component;
//
//
//public class MigrationTasklet implements Tasklet {
//
//
//    private BooksRelationRepository booksRelationRepository;
//
//    private AuthorsRelationRepository authorsRelationRepository;
//
//    @Override
//    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//        // Fetch data from Author and Book repositories
//        List<Author> authors = ...; // Fetch authors
//        List<Book> books = ...; // Fetch books
//
//        // Perform migration and save relationships to AuthorBookRelation table
//        for (Author author : authors) {
//            for (Book book : books) {
//                AuthorBookRelation relation = new AuthorBookRelation();
//                relation.setAuthor(author);
//                relation.setBook(book);
//                relationRepository.save(relation);
//            }
//        }
//
//        return RepeatStatus.FINISHED;
//    }
//}
