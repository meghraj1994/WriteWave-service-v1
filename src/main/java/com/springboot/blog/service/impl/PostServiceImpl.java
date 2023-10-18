package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;

    //Note : if a class is marked as entity and
    // there is only one constructor then we can omit @Autowired annotation
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    //convert DTO to entity method
    private Post mapToPost(PostDto postDto) {
        Post post = mapper.map(postDto,Post.class);
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }

    //convert entity to dto
    private PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post,PostDto.class);
//        postResponce.setTitle(post.getTitle());
//        postResponce.setDescription(post.getDescription());
//        postResponce.setContent(post.getContent());
        return postDto;

    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //convert DTO to entity
        Post post = mapToPost(postDto);

        //save it to db
        Post newPost = postRepository.save(post);

        //send response to client so first convert entity to dto
        PostDto postResponce = mapToDto(newPost);
        return postResponce;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        //create a pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        //get content from pages
        List<Post> newPosts = posts.getContent();
        List<PostDto> content = newPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {

        //get post by id from the db and if post is not exist then throw exeption
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        postDto.setContent(postDto.getContent());

        //save update value to database
        Post updatePost = postRepository.save(post);

        //convert post to postDto to send response to client
        return mapToDto(updatePost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);
    }

}
