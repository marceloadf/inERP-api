package com.inerpapi.repository.lancamento;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.inerpapi.model.Categoria_;
import com.inerpapi.model.Lancamento;
import com.inerpapi.model.Lancamento_;
import com.inerpapi.model.Pessoa_;
import com.inerpapi.repository.filter.LancamentoFilter;
import com.inerpapi.repository.projection.ResumoLancamento;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Lancamento> buscarLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);

        criteria.where(predicates);
        addSorting(pageable, builder, criteria, root);

        TypedQuery<Lancamento> query = entityManager.createQuery(criteria);
        addRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }

    @Override
    public Page<ResumoLancamento> buscarResumoLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ResumoLancamento> criteriaQuery = builder.createQuery(ResumoLancamento.class);
        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        criteriaQuery.select(builder.construct(ResumoLancamento.class, 
            root.get(Lancamento_.codigo),
            root.get(Lancamento_.descricao),
            root.get(Lancamento_.dataVencimento),
            root.get(Lancamento_.dataPagamento),
            root.get(Lancamento_.valor),
            root.get(Lancamento_.tipoLancamento),
            root.get(Lancamento_.categoria).get(Categoria_.nome),
            root.get(Lancamento_.pessoa).get(Pessoa_.nome)
            ));

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);

        criteriaQuery.where(predicates);
        addSorting(pageable, builder, criteriaQuery, root);

        TypedQuery<ResumoLancamento> query = entityManager.createQuery(criteriaQuery);
        addRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
            Root<Lancamento> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
            predicates.add(builder.like(builder.lower(root.get("descricao")),
                    "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
        }

        if (lancamentoFilter.getDataVencimentoDe() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoDe()));
        }

        if (lancamentoFilter.getDataVencimentoAte() != null) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoAte()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void addRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setMaxResults(totalRegistrosPorPagina);
        query.setFirstResult(primeiroRegistroDaPagina);

    }

    private void addSorting(Pageable pageable, CriteriaBuilder builder, CriteriaQuery<?> criteria,
            Root<Lancamento> root) {
        Iterator<Order> iterator = pageable.getSort().iterator();

        while (iterator.hasNext()) {
            Order order = iterator.next();

            if (order.isAscending())
                criteria.orderBy(builder.asc(root.get(order.getProperty())));
            else
                criteria.orderBy(builder.desc(root.get(order.getProperty())));
        }
    }

    private Long total(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Lancamento> root = criteriaQuery.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteriaQuery.where(predicates);

        criteriaQuery.select(builder.count(root));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}